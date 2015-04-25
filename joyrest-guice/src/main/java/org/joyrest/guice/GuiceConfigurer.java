package org.joyrest.guice;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.*;

import org.joyrest.aspect.Aspect;
import org.joyrest.context.AbstractConfigurer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.logging.JoyLogger;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import com.google.inject.*;

public class GuiceConfigurer extends AbstractConfigurer<Module> {

	private final static JoyLogger log = new JoyLogger(GuiceConfigurer.class);

	private Injector injector;

	@Override
	public ApplicationContext initialize(Module applicationConfig) {
		requireNonNull(applicationConfig, "Application module must be non-null for configuring Guice.");

		injector = Guice.createInjector(applicationConfig);
		return initializeContext();
	}

	@Override
	protected Collection<Aspect> getAspects() {
		return provide(new TypeLiteral<Set<Aspect>>() {});
	}

	@Override
	protected Collection<Reader> getReaders() {
		return provide(new TypeLiteral<Set<Reader>>() {});
	}

	@Override
	protected Collection<Writer> getWriters() {
		return provide(new TypeLiteral<Set<Writer>>() {});
	}

	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return provide(new TypeLiteral<Set<ExceptionConfiguration>>() {});
	}

	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return provide(new TypeLiteral<Set<ControllerConfiguration>>() {});
	}

	/*
	 * dagger provides an unmodifiable nullable set and this method convert this to modifiable list with non-null elements
	 */
	private <E> Collection<E> provide(TypeLiteral<Set<E>> type) {
		List<E> collection;
		try {
			Set<E> retrieved = injector.getInstance(Key.get(type));
			collection = retrieved.stream().filter(Objects::nonNull).collect(toList());

			if (collection.isEmpty())
				throw new ConfigurationException(new ArrayList<>());

		} catch (ConfigurationException ce) {
			log.info(() -> "There is no registered instance of the type: ");
			collection = new ArrayList<>();
		}

		return collection;
	}
}
