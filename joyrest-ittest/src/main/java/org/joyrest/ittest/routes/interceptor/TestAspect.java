package org.joyrest.ittest.routes.interceptor;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

public class TestAspect<REQ, RESP> implements Aspect<REQ, RESP> {

	@Override
	public InternalResponse<RESP> around(AspectChain<REQ, RESP> chain,
			InternalRequest<REQ> request, InternalResponse<RESP> response) {
		InternalResponse<RESP> resp = chain.proceed(request, response);
		return resp;
	}
}
