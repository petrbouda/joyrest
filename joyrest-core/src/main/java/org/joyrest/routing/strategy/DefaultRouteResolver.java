package org.joyrest.routing.strategy;

import static org.joyrest.exception.type.RestException.*;

import java.util.*;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.*;
import org.joyrest.stream.BiStream;
import org.joyrest.utils.OptionalChain;
import org.joyrest.validator.RequestMatcher;

public class DefaultRouteResolver implements RouteResolver {

    /* Class which compares path from route and incoming request */
    private final PathComparator pathComparator = new PathComparator();

    /* All routes configures in an application */
    private final Set<EntityRoute> routes;

    public DefaultRouteResolver(ApplicationContext context) {
        this.routes = context.getRoutes();
    }

    @Override
    public OptionalChain<EntityRoute> resolveRoute(InternalRequest<?> request) {
        Optional<EntityRoute> route = BiStream.of(routes.stream(), request)
                .throwFilter(pathComparator, notFoundSupplier())
                .throwFilter(RequestMatcher::matchNonEmptyList, notFoundSupplier())
                .throwFilter(RequestMatcher::matchHttpMethod, notFoundSupplier())
                .throwFilter(RequestMatcher::matchContentType, unsupportedMediaTypeSupplier())
                .throwFilter(RequestMatcher::matchAccept, notAcceptableSupplier())
                .findAny();

        return new OptionalChain<>(route);
    }
}
