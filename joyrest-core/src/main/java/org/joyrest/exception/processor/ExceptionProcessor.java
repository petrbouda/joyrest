package org.joyrest.exception.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

@FunctionalInterface
public interface ExceptionProcessor {

	<T extends Exception> Response process(T throwable, InternalRequest request, InternalResponse response)
		throws Exception;

}
