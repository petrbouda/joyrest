package org.joyrest.ittest.aspect;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.ittest.aspect.ContextHolder.RegistryKey.*;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class ThirdAspect implements Aspect {

	@Override
	public InternalResponse<Object> around(AspectChain chain, InternalRequest<Object> request, InternalResponse<Object> response) {
		if (isNull(ContextHolder.get(FIRST_KEY)) ||
			isNull(ContextHolder.get(SECOND_KEY)) ||
			nonNull(ContextHolder.get(THIRD_KEY)))
			throw new RuntimeException("Fail during Request side in FirstAspect");

		ContextHolder.put(THIRD_KEY, "yes");

		InternalResponse<Object> resp = chain.proceed(request, response);

		if (isNull(ContextHolder.get(FIRST_KEY)) ||
			isNull(ContextHolder.get(SECOND_KEY)) ||
			isNull(ContextHolder.get(THIRD_KEY)))
			throw new RuntimeException("Fail during Response side in FirstAspect");

		ContextHolder.put(THIRD_KEY, null);
		return resp;
	}

	@Override
	public int getOrder() {
		return 100;
	}
}
