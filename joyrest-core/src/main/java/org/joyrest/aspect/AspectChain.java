package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.Route;

public interface AspectChain<REQ, RESP> {

	InternalResponse<RESP> proceed(InternalRequest<REQ> request, InternalResponse<RESP> response);

	Route<REQ, RESP> getRoute();

}
