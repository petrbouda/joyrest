package org.joyrest.dagger;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import org.joyrest.aspect.Interceptor;
import org.joyrest.context.configurer.AbstractConfigurer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.ApplicationConfiguration;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import dagger.ObjectGraph;

public class DaggerConfigurer extends AbstractConfigurer<Object> {

	private final ApplicationConfiguration context = new ApplicationConfiguration();

	@Override
	public ApplicationContext initialize(Object applicationConfig) {
		requireNonNull(applicationConfig, "Application module must be non-null for configuring Dagger.");

		ObjectGraph graph = ObjectGraph.create(applicationConfig);
		DaggerConfigurationProvider provider = graph.inject(new DaggerConfigurationProvider());

		context.addAll(Interceptor.class, provider.interceptors);
		context.addAll(Reader.class, provider.readers);
		context.addAll(Writer.class, provider.writers);
		context.addAll(ExceptionConfiguration.class, provider.exceptionConfigurations);
		context.addAll(ControllerConfiguration.class, provider.controllerConfiguration);

		return initializeContext();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <B> Collection<B> getBeans(Class<B> clazz) {
		return (Collection<B>) context.get(clazz);
	}

	public static class DaggerConfigurationProvider {

		@Inject
		Set<Interceptor> interceptors;
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
