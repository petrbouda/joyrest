package org.joyrest.routing.strategy;

import static org.joyrest.exception.type.RestException.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.routing.PathComparator;
import org.joyrest.routing.Route;
import org.joyrest.stream.BiStream;
import org.joyrest.utils.OptionalChain;
import org.joyrest.validator.RequestValidator;

public class DefaultRouteResolver implements RouteResolver {

	/* Class which compares path from route and incoming request */
	private final PathComparator pathComparator = new PathComparator();

	/* Config contains everything about routes and other context details in this application */
	private final ApplicationContext context;

	/* All routes configures in an application */
	private final Set<Route> routes;

	public DefaultRouteResolver(ApplicationContext context) {
		this.context = context;
		this.routes = context.getRoutes();
	}

	@Override
	public OptionalChain<Route> resolveRoute(InternalRequest request) {
		final List<Route> routes = this.routes.stream()
			.filter(route -> pathComparator.test(route, request.getPathParts()))
			.collect(Collectors.toList());

		Optional<Route> route = BiStream.of(routes.stream(), request)
			.throwFilter(RequestValidator::validateNonEmptyList, notFoundSupplier())
			.throwFilter(RequestValidator::validateHttpMethod, notFoundSupplier())
			.throwFilter(RequestValidator::validateContentType, unsupportedMediaTypeSupplier())
			.throwFilter(RequestValidator::validateAccept, notAcceptableSupplier())
			.findAny();

		return new OptionalChain<>(route);
	}
}
