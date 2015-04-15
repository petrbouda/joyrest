package org.joyrest.exception.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

@FunctionalInterface
public interface ExceptionProcessor {

	<T extends Exception> InternalResponse<?> process(T ex, InternalRequest<?> request, InternalResponse<?> response)
			throws Exception;

}
