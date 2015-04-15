package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class AspectChainImpl implements AspectChain {

	private final Queue<Aspect> aspects = new ArrayDeque<>();

	private final EntityRoute route;

	public AspectChainImpl(EntityRoute route) {
		requireNonNull(route);
		this.route = route;
		this.aspects.addAll(route.getAspects());
	}

	@Override
	public InternalResponse<?> proceed(InternalRequest<?> request, InternalResponse<?> response) {
		requireNonNull(request);
		Aspect aspect = aspects.poll();

		if (nonNull(aspect))
			return aspect.around(this, request, response);

		return route.execute(request, response);
	}

	@Override
	public EntityRoute getRoute() {
		return route;
	}
}