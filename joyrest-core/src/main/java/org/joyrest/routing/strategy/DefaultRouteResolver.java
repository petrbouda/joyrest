package org.joyrest.routing.strategy;

import static org.joyrest.exception.type.RestException.*;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.*;
import org.joyrest.stream.BiStream;
import org.joyrest.utils.OptionalChain;
import org.joyrest.validator.RequestValidator;

public class DefaultRouteResolver implements RouteResolver {

    /* Class which compares path from route and incoming request */
    private final PathComparator pathComparator = new PathComparator();

    /* Config contains everything about routes and other context details in this application */
    private final ApplicationContext context;

    /* All routes configures in an application */
    private final Set<EntityRoute<?, ?>> routes;

    public DefaultRouteResolver(ApplicationContext context) {
        this.routes = context.getRoutes();
        this.context = context;
    }

    @Override
    public OptionalChain<EntityRoute<?, ?>> resolveRoute(InternalRequest<?> request) {
//		final List<EntityRoute<?, ?>> routes = this.routes.stream()
//			.filter(route -> pathComparator.test(route, request))
//			.collect(Collectors.toList());

        Optional<EntityRoute<?, ?>> route = BiStream.of(routes.stream(), request)
                .throwFilter(pathComparator, notFoundSupplier())
                .throwFilter(RequestValidator::validateNonEmptyList, notFoundSupplier())
                .throwFilter(RequestValidator::validateHttpMethod, notFoundSupplier())
                .throwFilter(RequestValidator::validateContentType, unsupportedMediaTypeSupplier())
                .throwFilter(RequestValidator::validateAccept, notAcceptableSupplier())
                .findAny();

        return new OptionalChain<>(route);
    }
}
