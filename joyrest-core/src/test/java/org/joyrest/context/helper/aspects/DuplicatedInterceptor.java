package org.joyrest.context.helper.aspects;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class DuplicatedInterceptor implements Interceptor {

	@Override
	public InternalResponse<Object> around(InterceptorChain chain,
			InternalRequest<Object> request, InternalResponse<Object> response) {

		return null;
	}

	@Override
	public int getOrder() {
		return -50;
	}
}
