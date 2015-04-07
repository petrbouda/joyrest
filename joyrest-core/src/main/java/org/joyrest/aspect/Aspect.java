package org.joyrest.aspect;

import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

@FunctionalInterface
public interface Aspect {

	Response around(AspectChain chain, Request request, Response response);

}
