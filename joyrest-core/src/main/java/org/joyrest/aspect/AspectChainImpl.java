package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.Route;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.Objects.requireNonNull;

public class AspectChainImpl<REQ, RESP> implements AspectChain<REQ, RESP> {

	private final Queue<Aspect<REQ, RESP>> aspects = new ArrayDeque<>();

	private final Route<REQ, RESP> route;

	public AspectChainImpl(Route<REQ, RESP> route) {
		requireNonNull(route, "Route cannot be null in AspectChain.");
		this.route = route;
		this.aspects.addAll(route.getAspects());
	}

	@Override
	public InternalResponse<RESP> proceed(InternalRequest<REQ> request, InternalResponse<RESP> response) {
		requireNonNull(request, "Request cannot be null.");
		Aspect<REQ, RESP> aspect = aspects.poll();

		if (aspect != null) {
			return aspect.around(this, request, response);
		}
		return route.execute(request, response);
	}

	@Override
	public Route<REQ, RESP> getRoute() {
		return route;
	}
}