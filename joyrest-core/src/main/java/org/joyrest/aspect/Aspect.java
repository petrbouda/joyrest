package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public interface Aspect {

	InternalResponse<Object> around(AspectChain chain, InternalRequest<Object> request, InternalResponse<Object> response);

	int getOrder();

}
