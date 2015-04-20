package org.joyrest.exception;

import java.util.Map;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public interface ExceptionConfiguration {

	void initialize();

	Map<Class<? extends Exception>, TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>> getExceptionHandlers();

}
