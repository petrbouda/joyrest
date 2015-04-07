package org.joyrest.exception;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExceptionConfiguration implements ExceptionConfiguration {

	/* Map of routes which are configured in an inherited class  */
	private final Map<Class<? extends Exception>,
		TriConsumer<Request, Response, ? extends Exception>> handlers = new HashMap<>();

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

	public <T extends Exception> void exception(Class<T> clazz, TriConsumer<Request, Response, T> handler) {
		handlers.put(clazz, handler);
	}

	@Override
	public Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>> getExceptionHandlers() {
		return handlers;
	}
}
