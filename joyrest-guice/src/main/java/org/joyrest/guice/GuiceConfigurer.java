package org.joyrest.guice;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Set;

import org.joyrest.aspect.Aspect;
import org.joyrest.context.AbstractConfigurer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import com.google.inject.*;

public class GuiceConfigurer extends AbstractConfigurer<Module> {

	private Injector injector;

	@Override
	public ApplicationContext initialize(Module applicationConfig) {
		requireNonNull(applicationConfig, "Application module must be non-null for configuring Guice.");

		injector = Guice.createInjector(applicationConfig);
		return initializeContext();
	}

	@Override
	protected Collection<Aspect> getAspects() {
		return injector.getInstance(Key.get(new TypeLiteral<Set<Aspect>>() {}));
	}

	@Override
	protected Collection<Reader> getReaders() {
		return injector.getInstance(Key.get(new TypeLiteral<Set<Reader>>() {}));
	}

	@Override
	protected Collection<Writer> getWriters() {
		return injector.getInstance(Key.get(new TypeLiteral<Set<Writer>>() {}));
	}

	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return injector.getInstance(Key.get(new TypeLiteral<Set<ExceptionConfiguration>>() {}));
	}

	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return injector.getInstance(Key.get(new TypeLiteral<Set<ControllerConfiguration>>() {}));
	}
}
