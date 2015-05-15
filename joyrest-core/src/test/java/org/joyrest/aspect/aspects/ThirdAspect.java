package org.joyrest.aspect.aspects;

import static org.joyrest.model.http.HeaderName.of;

import java.util.Map;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class ThirdAspect implements Aspect {

	@Override
	public InternalResponse<Object> around(AspectChain chain, InternalRequest<Object> request, InternalResponse<Object> response) {
		Map<HeaderName, String> headers = request.getHeaders();

		if (!"YES".equals(headers.get(of("1"))) ||
				!"YES".equals(headers.get(of("2"))) ||
				"YES".equals(headers.get(of("3"))))
			throw new RuntimeException("Fail during Request side in FirstAspect");

		headers.put(of("3"), "YES");

		InternalResponse<Object> resp = chain.proceed(request, response);

		if (!"YES".equals(headers.get(of("1"))) ||
				!"YES".equals(headers.get(of("2"))) ||
				!"YES".equals(headers.get(of("3"))))
			throw new RuntimeException("Fail during Response side in FirstAspect");

		headers.remove(of("3"));
		return resp;
	}

	@Override
	public int getOrder() {
		return 100;
	}
}
