package org.joyrest.exception;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

import java.util.Map;

public interface ExceptionConfiguration {

	void initialize();

	Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>> getExceptionHandlers();

}
