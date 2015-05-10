package org.joyrest.routing.strategy;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.utils.OptionalChain;

import java.util.Optional;

@FunctionalInterface
public interface RouteResolver {

	Optional<InternalRoute> resolveRoute(InternalRequest<?> request);

}
