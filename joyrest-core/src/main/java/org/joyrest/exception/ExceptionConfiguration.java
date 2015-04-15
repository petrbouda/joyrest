package org.joyrest.exception;

import java.util.Map;

import org.joyrest.exception.handler.ExceptionHandler;

public interface ExceptionConfiguration {

	void initialize();

	Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers();

}
