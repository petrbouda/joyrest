package org.joyrest.routing.strategy;

import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.utils.OptionalChain;

public class CachedRouteResolver implements RouteResolver {

	/* Config contains everything about routes and other context details in this application */
	private final ApplicationContext context;

	public CachedRouteResolver(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public OptionalChain<InternalRoute> resolveRoute(InternalRequest<?> request) {
		// TODO cache values into the ConcurrentHashMap
		return new OptionalChain<>(Optional.empty());
	}
}
