package org.joyrest.context.helper.aspects;

import org.joyrest.aspect.Interceptor;
import org.joyrest.aspect.InterceptorChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class FirstInterceptor implements Interceptor {

	public static final int ORDER = -50;

	@Override
	public InternalResponse<Object> around(InterceptorChain chain,
			InternalRequest<Object> request, InternalResponse<Object> response) {
		return null;
	}

	@Override
	public int getOrder() {
		return ORDER;
	}
}
