package org.joyrest.exception.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

@FunctionalInterface
public interface ExceptionProcessor {

	<T extends Exception> InternalResponse<Object> process(T ex, InternalRequest<Object> request,
			InternalResponse<Object> response) throws Exception;

}
