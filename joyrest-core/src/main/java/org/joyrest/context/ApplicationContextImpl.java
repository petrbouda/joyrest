package org.joyrest.context;

import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.collection.JoyCollections;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;
import org.joyrest.transform.WriterRegistrar;

import java.util.*;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

	private DefaultMultiMap<MediaType, WriterRegistrar> writers;

	/* Set of all configured items in this application */
	private final Set<Route> routes = new HashSet<>();
	private final Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>>
		exceptionHandlers = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addExceptionHandlers(Collection<ExceptionConfiguration> exceptionConfigurations) {
		Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>>
			tempHandlers = exceptionConfigurations.stream()
			.flatMap(config -> config.getExceptionHandlers().entrySet().stream())
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		exceptionHandlers.putAll(tempHandlers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRoutes(Set<Route> routes) {
		this.routes.addAll(routes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Route> getRoutes() {
		return Collections.unmodifiableSet(routes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefaultMultiMap<MediaType, WriterRegistrar> getWriters() {
		return writers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWriters(DefaultMultiMap<MediaType, WriterRegistrar> writers) {
		this.writers = JoyCollections.immutableOf(writers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>> getExceptionHandlers() {
		return Collections.unmodifiableMap(exceptionHandlers);
	}
}
