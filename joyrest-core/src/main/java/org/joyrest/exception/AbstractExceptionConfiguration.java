package org.joyrest.exception;

import java.util.HashMap;
import java.util.Map;

import org.joyrest.exception.handler.ExceptionHandler;

public abstract class AbstractExceptionConfiguration implements ExceptionConfiguration {

	/* Map of routes which are configured in an inherited class */
	private final Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> handlers = new HashMap<>();

	protected abstract void configure();

	/* RoutingConfiguration's initialization should be executed only once */
	private boolean isInitialized = false;

	@Override
	public final void initialize() {
		if (!this.isInitialized) {
			configure();

			this.isInitialized = true;
		}
	}

	public <T extends Exception> void exception(Class<T> clazz, ExceptionHandler<? super Exception> handler) {
		handlers.put(clazz, handler);
	}

	@Override
	public Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers() {
		return handlers;
	}
}
