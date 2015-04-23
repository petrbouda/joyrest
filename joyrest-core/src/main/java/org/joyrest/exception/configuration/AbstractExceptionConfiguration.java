package org.joyrest.exception.configuration;

import java.util.HashSet;
import java.util.Set;

import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.entity.Type;

public abstract class AbstractExceptionConfiguration implements ExceptionConfiguration {

	/* Set of handlers which are configured in an inherited class */
	private final Set<InternalExceptionHandler> handlers = new HashSet<>();

	/* ExceptionConfiguration's initialization should be executed only once */
	private boolean isInitialized = false;

	protected abstract void configure();

	@Override
	public final void initialize() {
		if (!this.isInitialized) {
			configure();

			this.isInitialized = true;
		}
	}

	@Override
	public Set<InternalExceptionHandler> getExceptionHandlers() {
		return handlers;
	}

	public <T extends Exception, RESP> InternalExceptionHandler putHandler(Class<T> clazz,
			TriConsumer<Request<?>, Response<RESP>, T> consumer, Type<RESP> resp) {
		final InternalExceptionHandler handler = new InternalExceptionHandler(clazz, consumer, resp);
		handlers.add(handler);
		return handler;
	}
}
