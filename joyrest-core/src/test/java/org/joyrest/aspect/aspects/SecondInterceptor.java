package org.joyrest.aspect.aspects;

import static org.joyrest.model.http.HeaderName.of;

import java.util.Map;

import org.joyrest.aspect.Interceptor;
import org.joyrest.aspect.InterceptorChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class SecondInterceptor implements Interceptor {

	@Override
	public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> request, InternalResponse<Object> response) {
		Map<HeaderName, String> headers = request.getHeaders();

		if (!"YES".equals(headers.get(of("1"))) ||
				"YES".equals(headers.get(of("2"))) ||
				"YES".equals(headers.get(of("3"))))
			throw new RuntimeException("Fail during Request side in FirstAspect");

		headers.put(of("2"), "YES");

		InternalResponse<Object> resp = chain.proceed(request, response);

		if (!"YES".equals(headers.get(of("1"))) ||
				!"YES".equals(headers.get(of("2"))) ||
				"YES".equals(headers.get(of("3"))))
			throw new RuntimeException("Fail during Response side in FirstAspect");

		headers.remove(of("2"));
		return resp;
	}

	@Override
	public int getOrder() {
		return 50;
	}
}
