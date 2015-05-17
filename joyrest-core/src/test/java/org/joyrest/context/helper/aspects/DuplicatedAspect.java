package org.joyrest.context.helper.aspects;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class DuplicatedAspect implements Aspect {

	@Override
	public InternalResponse<Object> around(AspectChain chain,
			InternalRequest<Object> request, InternalResponse<Object> response) {

		return null;
	}

	@Override
	public int getOrder() {
		return -50;
	}
}
