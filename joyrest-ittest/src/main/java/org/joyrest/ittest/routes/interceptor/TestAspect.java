package org.joyrest.ittest.routes.interceptor;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

public class TestAspect implements Aspect {

	@Override
	public Response around(AspectChain chain, Request request, Response response) {
		Response resp = chain.proceed(request, response);
		return resp;
	}

}
