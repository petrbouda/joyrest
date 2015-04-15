package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

@FunctionalInterface
public interface Aspect {

	InternalResponse<?> around(AspectChain chain, InternalRequest<?> request, InternalResponse<?> response);

}
