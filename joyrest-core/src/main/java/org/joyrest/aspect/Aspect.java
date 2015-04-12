package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

@FunctionalInterface
public interface Aspect<REQ, RESP> {

	InternalResponse<RESP> around(AspectChain<REQ, RESP> chain, InternalRequest<REQ> request, InternalResponse<RESP> response);

}
