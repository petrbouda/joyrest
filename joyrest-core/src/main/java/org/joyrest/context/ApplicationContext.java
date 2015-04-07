package org.joyrest.context;

import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.transform.WriterRegistrar;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The heart of the JoyREST Framework that contains all needed configuration for successful running
 * the framework. It doesn't do any work just store the information about the framework and
 * application that is built on that.
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
	void addRoutes(Set<Route> routes);

	/**
	 * Returns all instances of {@link org.joyrest.routing.SimpleRoute} that were added into an application
	 *
	 * @return collection of {@link org.joyrest.routing.SimpleRoute} configured into an application
	 */
	Set<Route> getRoutes();

	/**
	 * Returns all writers registered belongs to the route
	 *
	 * @return {@link Writer} according to the given media type
	 */
	DefaultMultiMap<MediaType, WriterRegistrar> getWriters();

	/**
	 * Registers all readers belongs to the route
	 *
	 * @param writers {@link Map} with collection of {@code writers}
	 */
	void setWriters(DefaultMultiMap<MediaType, WriterRegistrar> writers);

	/**
	 * Returns all instances of exception handlers
	 * that were added into an application
	 *
	 * @return map of exception handlers configured into an application
	 */
	Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>> getExceptionHandlers();
}
