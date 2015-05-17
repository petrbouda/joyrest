package org.joyrest.dagger;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import org.joyrest.aspect.Aspect;
import org.joyrest.context.AbstractConfigurer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import dagger.ObjectGraph;

public class DaggerConfigurer extends AbstractConfigurer<Object> {

	private DaggerConfigurationProvider provider = null;

	@Override
	public ApplicationContext initialize(Object applicationConfig) {
		requireNonNull(applicationConfig, "Application module must be non-null for configuring Dagger.");

		ObjectGraph graph = ObjectGraph.create(applicationConfig);
		provider = graph.inject(new DaggerConfigurationProvider());
		return initializeContext();
	}

	@Override
	protected Collection<Aspect> getAspects() {
		return createList(provider.aspects);
	}

	@Override
	protected Collection<Reader> getReaders() {
		return createList(provider.readers);
	}

	@Override
	protected Collection<Writer> getWriters() {
		return createList(provider.writers);
	}

	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return createList(provider.exceptionConfigurations);
	}

	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return createList(provider.controllerConfiguration);
	}

	/*
	 * dagger provides an unmodifiable nullable set and this method convert this to modifiable list with non-null elements
	 */
	private <E> Collection<E> createList(Collection<E> collection) {
		return collection.stream()
			.filter(Objects::nonNull)
			.collect(toList());
	}

	public static class DaggerConfigurationProvider {

		@Inject
		Set<Aspect> aspects;
		@Inject
		Set<Reader> readers;
		@Inject
		Set<Writer> writers;
		@Inject
		Set<ExceptionConfiguration> exceptionConfigurations;
		@Inject
		Set<ControllerConfiguration> controllerConfiguration;
	}
}