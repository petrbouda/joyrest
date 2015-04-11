package org.joyrest.context;

import java.util.*;
import java.util.stream.Collectors;

import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;

public class ApplicationContextImpl implements ApplicationContext {

	/* Set of all configured items in this application */
	private final Set<EntityRoute<?, ?>> routes = new HashSet<>();

	private final Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> exceptionHandlers = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addExceptionHandlers(Collection<ExceptionConfiguration> exceptionConfigurations) {
		Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> handlers =
				exceptionConfigurations.stream()
					.flatMap(config -> config.getExceptionHandlers().entrySet().stream())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		exceptionHandlers.putAll(handlers);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param routes
	 */
	@Override
	public void addRoutes(Set<EntityRoute<?, ?>> routes) {
		this.routes.addAll(routes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<EntityRoute<?, ?>> getRoutes() {
		return Collections.unmodifiableSet(routes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers() {
		return Collections.unmodifiableMap(exceptionHandlers);
	}
}
