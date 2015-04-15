package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;

public interface AspectChain {

	InternalResponse<?> proceed(InternalRequest<?> request, InternalResponse<?> response);

	EntityRoute getRoute();

}
