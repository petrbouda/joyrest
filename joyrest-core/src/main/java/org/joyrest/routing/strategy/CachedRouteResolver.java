package org.joyrest.routing.strategy;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.utils.OptionalChain;

import java.util.Optional;

public class CachedRouteResolver implements RouteResolver {

	/* Config contains everything about routes and other context details in this application */
	private final ApplicationContext context;

	public CachedRouteResolver(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public OptionalChain<EntityRoute<?,?>> resolveRoute(InternalRequest<?> request) {
		// TODO cache values into the ConcurrentHashMap
		return new OptionalChain<>(Optional.empty());
	}
}
