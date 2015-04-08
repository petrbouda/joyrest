package org.joyrest.exception;

import org.joyrest.exception.handler.ExceptionHandler;
import java.util.Map;

public interface ExceptionConfiguration {

	void initialize();

	Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers();

}
