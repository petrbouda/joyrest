package org.joyrest.routing.strategy;

import static org.joyrest.exception.type.RestException.*;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import java.util.Optional;
import java.util.Set;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.PathComparator;
import org.joyrest.stream.BiStream;
import org.joyrest.utils.OptionalChain;
import org.joyrest.validator.RequestMatcher;

public class DefaultRouteResolver implements RouteResolver {

	/* Class which compares path from route and incoming request */
	private final PathComparator pathComparator = new PathComparator();

	/* All routes configures in an application */
	private final Set<InternalRoute> routes;

	public DefaultRouteResolver(ApplicationContext context) {
		this.routes = context.getRoutes();
	}

	@Override
	public OptionalChain<InternalRoute> resolveRoute(InternalRequest<?> request) {
		Optional<InternalRoute> route = BiStream.of(routes.stream(), request)
			.throwFilter(pathComparator, notFoundSupplier(String.format(
					"There is no route suitable for path [%s]",
					request.getPath())))
			.throwFilter(RequestMatcher::matchHttpMethod, notFoundSupplier(String.format(
					"There is no route suitable for path [%s], method [%s]",
					request.getPath(), request.getMethod())))
			.throwFilter(RequestMatcher::matchContentType, unsupportedMediaTypeSupplier(String.format(
					"There is no route suitable for path [%s], method [%s], content-type [%s]",
					request.getPath(), request.getMethod(), request.getHeader(CONTENT_TYPE).get())))
			.throwFilter(RequestMatcher::matchAccept, notAcceptableSupplier(String.format(
					"There is no route suitable for path [%s], method [%s], accept [%s]",
					request.getPath(), request.getMethod(), request.getHeader(ACCEPT))))
			.findAny();

		return new OptionalChain<>(route);
	}
}
