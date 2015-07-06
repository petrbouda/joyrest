package org.joyrest.ittest.aspect;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.ittest.aspect.ContextHolder.RegistryKey.*;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class FirstInterceptor implements Interceptor {

	@Override
	public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> request, InternalResponse<Object> response) {
		if (nonNull(ContextHolder.get(FIRST_KEY)) ||
			nonNull(ContextHolder.get(SECOND_KEY)) ||
			nonNull(ContextHolder.get(THIRD_KEY)))
			throw new RuntimeException("Fail during Request side in FirstAspect");

		ContextHolder.put(FIRST_KEY, "yes");

		InternalResponse<Object> resp = chain.proceed(request, response);

		if (isNull(ContextHolder.get(FIRST_KEY)) ||
			nonNull(ContextHolder.get(SECOND_KEY)) ||
			nonNull(ContextHolder.get(THIRD_KEY)))
			throw new RuntimeException("Fail during Response side in FirstAspect");

		resp.header(HeaderName.of("result"), "success");
		return resp;
	}

	@Override
	public int getOrder() {
		return -50;
	}
}
