package org.joyrest.exception;

import java.util.Set;

import org.joyrest.exception.handler.InternalExceptionHandler;

public interface ExceptionConfiguration {

	void initialize();

	Set<InternalExceptionHandler> getExceptionHandlers();

}
