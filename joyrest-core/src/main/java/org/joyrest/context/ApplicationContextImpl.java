package org.joyrest.context;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.Route;

public class ApplicationContextImpl implements ApplicationContext {

	/* Set of all configured items in this application */
	private Set<InternalRoute> routes = new HashSet<>();

	private Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<InternalRoute> getRoutes() {
		return unmodifiableSet(routes);
	}

	/**
	 * Adds a collection of {@link Route} into the application
	 *
	 * @param routes set of routes defined in application
	 */
	public void setRoutes(Set<InternalRoute> routes) {
		requireNonNull(routes);
		this.routes = routes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionHandlers() {
		return unmodifiableMap(exceptionHandlers);
	}

	/**
	 * Adds a map of {@link ExceptionHandler} into the application
	 *
	 * @param handlers configurations which keep given handlers
	 */
	public void setExceptionHandlers(Map<Class<? extends Exception>, InternalExceptionHandler> handlers) {
		requireNonNull(handlers);
		this.exceptionHandlers = handlers;
	}

}
