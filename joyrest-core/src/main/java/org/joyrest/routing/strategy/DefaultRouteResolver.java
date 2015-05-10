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
import org.joyrest.routing.matcher.RequestMatcher;
import org.joyrest.stream.BiStream;

public class DefaultRouteResolver implements RouteResolver {

	/* Class which compares path from route and incoming model */
	private final PathComparator pathComparator = new PathComparator();

	/* All routes configures in an application */
	private final Set<InternalRoute> routes;

	public DefaultRouteResolver(ApplicationContext context) {
		this.routes = context.getRoutes();
	}

	@Override
	public Optional<InternalRoute> resolveRoute(InternalRequest<?> request) {
		return BiStream.of(routes.stream(), request)
			.throwIfNull(pathComparator, notFoundSupplier(String.format(
					"There is no route suitable for path [%s]",
					request.getPath())))
			.throwIfNull(RequestMatcher::matchHttpMethod, notFoundSupplier(String.format(
					"There is no route suitable for path [%s], method [%s]",
					request.getPath(), request.getMethod())))
			.throwIfNull(RequestMatcher::matchConsumes, unsupportedMediaTypeSupplier(String.format(
					"There is no route suitable for path [%s], method [%s], content-type [%s]",
					request.getPath(), request.getMethod(), request.getHeader(CONTENT_TYPE).orElse("---"))))
			.throwIfNull(RequestMatcher::matchProduces, notAcceptableSupplier(String.format(
					"There is no route suitable for path [%s], method [%s], accept [%s]",
					request.getPath(), request.getMethod(), request.getHeader(ACCEPT))))
			.findAny();
	}
}
