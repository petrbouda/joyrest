package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

public interface AspectChain {

	InternalResponse<Object> proceed(InternalRequest<Object> request, InternalResponse<Object> response);

	InternalRoute getRoute();

}
