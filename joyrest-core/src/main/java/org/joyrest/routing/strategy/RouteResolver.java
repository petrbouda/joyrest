package org.joyrest.routing.strategy;

import java.util.Optional;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;

@FunctionalInterface
public interface RouteResolver {

	Optional<InternalRoute> resolveRoute(InternalRequest<?> request);

}
