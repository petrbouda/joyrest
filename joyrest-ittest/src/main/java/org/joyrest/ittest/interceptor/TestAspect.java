package org.joyrest.ittest.interceptor;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

public class TestAspect implements Aspect {

	@Override
	public InternalResponse<?> around(AspectChain chain, InternalRequest<?> request, InternalResponse<?> response) {
		InternalResponse<?> resp = chain.proceed(request, response);
		return resp;
	}
}
