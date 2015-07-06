package org.joyrest.dagger;

import java.util.*;
import java.util.function.Supplier;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.AbstractConfigurer;
import org.joyrest.dagger.template.ConfigurationTemplate;
import org.joyrest.logging.JoyLogger;

public class DaggerConfigurer extends AbstractConfigurer<Object> {

	private static final JoyLogger log = new JoyLogger(DaggerConfigurer.class);

	@SuppressWarnings("rawtypes")
	private final Map<Class, Supplier> beanFactory = new HashMap<>();

	public ApplicationContext initialize() {
		return initializeContext();
	}

	@Override
	public ApplicationContext initialize(Object nullConfiguration) {
		throw new UnsupportedOperationException("This method is not supported for initialization of Dagger application, " +
				"use DaggerConfigurer#initialize() method instead.");
	}

	public <B> DaggerConfigurer addSupplier(Class<B> clazz, Supplier<Set<B>> supplier) {
		beanFactory.put(clazz, supplier);
		return this;
	}

	public DaggerConfigurer addDaggerTemplate(ConfigurationTemplate template) {
		template.getSuppliers().forEach(beanFactory::putIfAbsent);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <B> Collection<B> getBeans(Class<B> clazz) {
		try {
			return (Collection<B>) beanFactory.get(clazz).get();
		} catch (NullPointerException npe) {
			log.warn(() -> "There is no registered bean of the type " + clazz.getSimpleName());
			return Collections.emptySet();
		}
	}
}
