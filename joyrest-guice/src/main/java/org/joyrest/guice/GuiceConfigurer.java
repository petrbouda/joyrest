package org.joyrest.guice;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.AbstractConfigurer;
import org.joyrest.logging.JoyLogger;

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
	public <B> List<B> getBeans(Class<B> clazz) {
		return provide(new TypeLiteral<Set<B>>() {});
	}

	/*
	 * dagger provides an unmodifiable nullable set and this method convert this to modifiable list with non-null elements
	 */
	private <E> List<E> provide(TypeLiteral<Set<E>> type) {
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
