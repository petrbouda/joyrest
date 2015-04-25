package org.joyrest.context;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public abstract class NonDiConfigurer extends AbstractConfigurer<Object> {

	private final Collection<Aspect> aspects = new HashSet<>();
	private final Collection<Reader> readers = new HashSet<>();
	private final Collection<Writer> writers = new HashSet<>();
	private final Collection<ExceptionConfiguration> exceptionConfigurations = new HashSet<>();
	private final Collection<ControllerConfiguration> controllerConfigurations = new HashSet<>();

	abstract protected ApplicationContext initialize();

	protected void addAspect(Aspect aspect) {
		requireNonNull(aspect, "A registered aspect cannot be null.");
		aspects.add(aspect);
	}

	protected void addReader(Reader reader) {
		requireNonNull(reader, "A registered reader cannot be null.");
		readers.add(reader);
	}

	protected void addWriter(Writer writer) {
		requireNonNull(writer, "A registered writer cannot be null.");
		writers.add(writer);
	}

	protected void addExceptionConfiguration(ExceptionConfiguration exceptionConfiguration) {
		requireNonNull(exceptionConfiguration, "A registered exception configuration cannot be null.");
		exceptionConfigurations.add(exceptionConfiguration);
	}

	protected void addControllerConfiguration(ControllerConfiguration controllerConfiguration) {
		requireNonNull(controllerConfiguration, "A registered controller configuration cannot be null.");
		controllerConfigurations.add(controllerConfiguration);
	}

	@Override
	protected final Collection<Aspect> getAspects() {
		return aspects;
	}

	@Override
	protected final Collection<Reader> getReaders() {
		return readers;
	}

	@Override
	protected final Collection<Writer> getWriters() {
		return writers;
	}

	@Override
	protected final Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return exceptionConfigurations;
	}

	@Override
	protected final Collection<ControllerConfiguration> getControllerConfiguration() {
		return controllerConfigurations;
	}

	@Override
	public ApplicationContext initialize(Object applicationConfig) {
		throw new UnsupportedOperationException("Non-DI Configurer does not support this method.");
	}

}
