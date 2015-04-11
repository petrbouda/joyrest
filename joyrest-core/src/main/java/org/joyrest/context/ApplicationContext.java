package org.joyrest.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;

/**
 * The heart of the JoyREST Framework that contains all needed configuration for successful running the framework. It doesn't do any work
 * just store the information about the framework and application that is built on that.
 *
 * @author pbouda
 */
public interface ApplicationContext {

	/**
	 * Adds a {@link ExceptionHandler} into the application
	 *
	 * @param exceptionConfigurations configurations which keep given handlers
	 */
	void addExceptionHandlers(Collection<ExceptionConfiguration> exceptionConfigurations);

	/**
	 * Adds a collection of {@link Route} into the application
	 *
	 * @param routes set of routes defined in application
	 */
	void addRoutes(Set<EntityRoute<?, ?>> routes);

	/**
	 * Returns all instances of {@link org.joyrest.routing.EntityRoute} that were added into an application
	 *
	 * @return collection of {@link org.joyrest.routing.EntityRoute} configured into an application
	 */
	Set<EntityRoute<?, ?>> getRoutes();

	/**
	 * Returns all instances of exception handlers that were added into an application
	 *
	 * @return map of exception handlers configured into an application
	 */
	Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers();
}
