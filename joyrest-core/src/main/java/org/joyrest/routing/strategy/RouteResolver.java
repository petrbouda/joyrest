package org.joyrest.routing.strategy;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.utils.OptionalChain;

@FunctionalInterface
public interface RouteResolver {

	OptionalChain<InternalRoute> resolveRoute(InternalRequest<?> request);

}
