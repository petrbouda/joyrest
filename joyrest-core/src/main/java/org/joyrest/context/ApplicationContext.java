package org.joyrest.context;

import java.util.Map;
import java.util.Set;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.EntityRoute;
import org.joyrest.transform.Writer;

/**
 * The heart of the JoyREST Framework that contains all needed configuration for successful running the framework. It doesn't do any work
 * just store the information about the framework and application that is built on that.
 *
 * @author pbouda
 */
public interface ApplicationContext {

	/**
	 * Returns all instances of {@link org.joyrest.routing.EntityRoute} that were added into an application
	 *
	 * @return collection of {@link org.joyrest.routing.EntityRoute} configured into an application
	 */
	Set<EntityRoute> getRoutes();

	/**
	 * Returns all instances of exception handlers that were added into an application
	 *
	 * @return map of exception handlers configured into an application
	 */
	Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> getExceptionHandlers();

	/**
	 * Returns all instances of exception writers that were added into an application
	 *
	 * @return map of exception writers configured into an application
	 */
	Map<MediaType, Writer> getExceptionWriters();
}
