package org.joyrest.aspect;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.Queue;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

public class AspectChainImpl implements AspectChain {

	private final Queue<Aspect> aspects = new ArrayDeque<>();

	private final InternalRoute route;

	public AspectChainImpl(InternalRoute route) {
		requireNonNull(route);
		this.route = route;
		this.aspects.addAll(route.getAspects());
	}

	@Override
	public InternalResponse<Object> proceed(InternalRequest<Object> request, InternalResponse<Object> response) {
		requireNonNull(request);
		Aspect aspect = aspects.poll();

		if (nonNull(aspect))
			return aspect.around(this, request, response);

		return route.execute(request, response);
	}

	@Override
	public InternalRoute getRoute() {
		return route;
	}
}