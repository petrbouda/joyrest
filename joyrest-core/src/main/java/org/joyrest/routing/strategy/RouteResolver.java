package org.joyrest.routing.strategy;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;
import org.joyrest.utils.OptionalChain;

@FunctionalInterface
public interface RouteResolver {

	OptionalChain<Route> resolveRoute(InternalRequest request);


}
