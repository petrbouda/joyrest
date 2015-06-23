package org.joyrest.spring;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.DependencyInjectionConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Class that is able to configure JoyREST Framework for Spring Dependency Injection Framework.
 *
 * @author pbouda
 */
public final class SpringJavaConfigurer extends DependencyInjectionConfigurer<Object> {

	private org.springframework.context.ApplicationContext context = null;

	@Override
	public ApplicationContext initialize(Object applicationConfig) {
		requireNonNull(applicationConfig, "ApplicationConfig must be non-null for configuring Spring.");

		boolean isConfigAnnotated = applicationConfig.getClass().isAnnotationPresent(Configuration.class);
		if (!isConfigAnnotated)
			throw new IllegalArgumentException("Provided config is @Configuration annotated Spring Java Config");

		context = new AnnotationConfigApplicationContext(applicationConfig.getClass());
		return initializeContext();
	}

	@Override
	protected <B> List<B> getBeans(Class<B> clazz) {
		return new ArrayList<>(context.getBeansOfType(clazz).values());
	}
}
