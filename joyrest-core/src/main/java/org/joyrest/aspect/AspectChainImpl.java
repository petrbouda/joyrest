package org.joyrest.aspect;

import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.Queue;

import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;

public class AspectChainImpl implements AspectChain {

	private final Queue<Aspect> aspects = new ArrayDeque<>();

	private final Route route;

	public AspectChainImpl(Route route) {
		requireNonNull(route, "Route cannot be null in AspectChain.");
		this.route = route;
		this.aspects.addAll(route.getAspects());
	}

	@Override
	public Response proceed(Request request, Response response) {
		requireNonNull(request, "Request cannot be null.");
		Aspect aspect = aspects.poll();

		if (aspect != null) {
			return aspect.around(this, request, response);
		}
		return route.execute(request, response);
	}

	@Override
	public Route getRoute() {
		return route;
	}
}