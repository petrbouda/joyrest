package org.joyrest.context;

import java.util.Collection;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public abstract class DependencyInjectionConfigurer<T> extends AbstractConfigurer<T> {

	protected abstract <B> Collection<B> getBeans(Class<B> clazz);

	@Override
	protected Collection<Aspect> getAspects() {
		return getBeans(Aspect.class);
	}

	@Override
	protected Collection<Reader> getReaders() {
		return getBeans(Reader.class);
	}

	@Override
	protected Collection<Writer> getWriters() {
		return getBeans(Writer.class);
	}

	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return getBeans(ExceptionConfiguration.class);
	}

	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return getBeans(ControllerConfiguration.class);
	}

}
