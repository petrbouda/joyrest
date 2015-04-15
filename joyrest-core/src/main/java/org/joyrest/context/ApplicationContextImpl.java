package org.joyrest.context;

import static java.util.Objects.requireNonNull;

import java.util.*;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Writer;

public class ApplicationContextImpl implements ApplicationContext {

	/* Set of all configured items in this application */
	private Set<EntityRoute> routes = new HashSet<>();

	private Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> exceptionHandlers = new HashMap<>();

	private Map<MediaType, Writer> exceptionWriters;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<EntityRoute> getRoutes() {
		return Collections.unmodifiableSet(routes);
	}

	/**
	 * Adds a collection of {@link Route} into the application
	 *
	 * @param routes set of routes defined in application
	 */
	public void setRoutes(Set<EntityRoute> routes) {
		requireNonNull(routes);
		this.routes = routes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers() {
		return Collections.unmodifiableMap(exceptionHandlers);
	}

	/**
	 * Adds a map of {@link ExceptionHandler} into the application
	 *
	 * @param exceptionHandlers configurations which keep given handlers
	 */
	public void setExceptionHandlers(Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> exceptionHandlers) {
		requireNonNull(exceptionHandlers);
		this.exceptionHandlers = exceptionHandlers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<MediaType, Writer> getExceptionWriters() {
		return Collections.unmodifiableMap(exceptionWriters);
	}

	/**
	 * Adds a map of {@link Writer} into the application
	 *
	 * @param exceptionWriters set of writer for exception processing defined in application
	 */
	public void setExceptionWriters(Map<MediaType, Writer> exceptionWriters) {
		requireNonNull(exceptionWriters);
		this.exceptionWriters = exceptionWriters;
	}
}
