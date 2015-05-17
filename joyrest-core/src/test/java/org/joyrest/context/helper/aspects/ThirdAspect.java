package org.joyrest.context.helper.aspects;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class ThirdAspect implements Aspect {

	public static final int ORDER = 100;

	@Override
	public InternalResponse<Object> around(AspectChain chain, 
		InternalRequest<Object> request, InternalResponse<Object> response) {
		return null;
	}

	@Override
	public int getOrder() {
		return ORDER;
	}
}
