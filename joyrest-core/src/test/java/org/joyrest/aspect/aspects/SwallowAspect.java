package org.joyrest.aspect.aspects;

import java.util.Map;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class SwallowAspect implements Aspect {

	@Override
	public InternalResponse<Object> around(AspectChain chain, InternalRequest<Object> request, InternalResponse<Object> response) {
		Map<HeaderName, String> headers = request.getHeaders();

		headers.put(HeaderName.of("Swallowed"), "YES");

		return response;
	}

	@Override
	public int getOrder() {
		return -50;
	}
}
