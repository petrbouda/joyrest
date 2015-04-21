package org.joyrest.context;

import java.util.List;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public abstract class DependencyInjectionConfigurer<T> extends AbstractConfigurer<T> {

	protected abstract <B> List<B> getBeans(Class<B> clazz);

	@Override
	protected List<Aspect> getAspects() {
		return getBeans(Aspect.class);
	}

	@Override
	protected List<Reader> getReaders() {
		return getBeans(Reader.class);
	}

	@Override
	protected List<Writer> getWriters() {
		return getBeans(Writer.class);
	}

	@Override
	protected List<ExceptionConfiguration> getExceptionConfigurations() {
		return getBeans(ExceptionConfiguration.class);
	}

	@Override
	protected List<ControllerConfiguration> getControllerConfiguration() {
		return getBeans(ControllerConfiguration.class);
	}

}
