package org.joyrest.exception;

import java.util.HashMap;
import java.util.Map;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public abstract class AbstractExceptionConfiguration implements ExceptionConfiguration {

	/* Map of routes which are configured in an inherited class */
	protected final Map<Class<? extends Exception>, TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>>
		handlers = new HashMap<>();

	/* RoutingConfiguration's initialization should be executed only once */
	private boolean isInitialized = false;

	protected abstract void configure();

	@Override
	public final void initialize() {
		if (!this.isInitialized) {
			configure();

			this.isInitialized = true;
		}
	}

	public <T extends Exception> void putHandler(Class<T> clazz, TriConsumer<?, ?, T> handler) {
		handlers.put(clazz, (TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>) handler);
	}

	@Override
	public Map<Class<? extends Exception>, TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>> getExceptionHandlers() {
		return handlers;
	}
}
