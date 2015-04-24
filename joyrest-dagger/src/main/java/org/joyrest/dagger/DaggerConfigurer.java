package org.joyrest.dagger;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
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

		ObjectGraph objectGraph = ObjectGraph.create(applicationConfig);
		provider = objectGraph.inject(new DaggerConfigurationProvider());
		return initializeContext();
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

	@Override
	protected Collection<Aspect> getAspects() {
		return provider.aspects;
	}

	@Override
	protected Collection<Reader> getReaders() {
		return provider.readers;
	}

	@Override
	protected Collection<Writer> getWriters() {
		return provider.writers;
	}

	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return provider.exceptionConfigurations;
	}

	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return provider.controllerConfiguration;
	}

}
