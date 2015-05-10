package org.joyrest.exception.configuration;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.exception.handler.ExceptionHandlerAction;
import org.joyrest.routing.entity.ResponseCollectionType;
import org.joyrest.routing.entity.ResponseType;
import org.joyrest.routing.entity.Type;

public abstract class TypedExceptionConfiguration extends AbstractExceptionConfiguration {

	protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz,
			ExceptionHandlerAction<RESP, T> handler) {
		return putHandler(clazz, handler, null);
	}

	protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz,
			ExceptionHandlerAction<RESP, T> handler, Class<RESP> reqResp) {
		return putHandler(clazz, handler, new Type<>(reqResp));
	}

	protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz,
			ExceptionHandlerAction<RESP, T> handler, ResponseType<RESP> resp) {
		return putHandler(clazz, handler, resp);
	}

	protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz,
			ExceptionHandlerAction<RESP, T> handler, ResponseCollectionType<RESP> resp) {
		return putHandler(clazz, handler, resp);
	}

}
