package org.joyrest.context;

import java.util.Map;
import java.util.Set;

import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;

/**
 * The heart of the JoyREST Framework that contains all needed configuration for successful running the framework. It doesn't do any work
 * just store the information about the framework and application that is built on that.
 *
 * @author pbouda
 */
public interface ApplicationContext {

	/**
	 * Returns all instances of {@link InternalRoute} that were added into an application
	 *
	 * @return collection of {@link InternalRoute} configured into an application
	 */
	Set<InternalRoute> getRoutes();

	/**
	 * Returns all instances of exception handlers that were added into an application
	 *
	 * @return map of exception handlers configured into an application
	 */
	Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionHandlers();

}
