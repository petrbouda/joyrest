/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.context;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

/**
 * Abstract Configurer which is mainly used for an implementation of a new configurer that is not based on any
 * Dependency Injection Framework.
 *
 * {@inheritDoc}
 */
public abstract class NonDiConfigurer extends AbstractConfigurer<Object> {

	private final Collection<Aspect> aspects = new HashSet<>();
	private final Collection<Reader> readers = new HashSet<>();
	private final Collection<Writer> writers = new HashSet<>();
	private final Collection<ExceptionConfiguration> exceptionConfigurations = new HashSet<>();
	private final Collection<ControllerConfiguration> controllerConfigurations = new HashSet<>();

	/**
	 * Method in which is application context of {@link NonDiConfigurer} initialized. After a registering all useful beans is
	 * needed to call the method {@link AbstractConfigurer#initializeContext()} that finishes the creation of the application
	 * context.
	 *
	 * @return application context with all registered beans
	 */
	abstract public ApplicationContext initialize();

	/**
	 * Add a new {@link Aspect} to the application context.
	 *
	 * @param aspect registered aspect
	 */
	protected void addAspect(Aspect aspect) {
		requireNonNull(aspect, "A registered aspect cannot be null.");
		aspects.add(aspect);
	}

	/**
	 * Add a new {@link Reader} to the application context.
	 *
	 * @param reader registered aspect
	 */
	protected void addReader(Reader reader) {
		requireNonNull(reader, "A registered reader cannot be null.");
		readers.add(reader);
	}

	/**
	 * Add a new {@link Writer} to the application context.
	 *
	 * @param writer registered aspect
	 */
	protected void addWriter(Writer writer) {
		requireNonNull(writer, "A registered writer cannot be null.");
		writers.add(writer);
	}

	/**
	 * Add a new {@link ExceptionConfiguration} to the application context.
	 *
	 * @param exceptionConfiguration registered aspect
	 */
	protected void addExceptionConfiguration(ExceptionConfiguration exceptionConfiguration) {
		requireNonNull(exceptionConfiguration, "A registered exception configuration cannot be null.");
		exceptionConfigurations.add(exceptionConfiguration);
	}

	/**
	 * Add a new {@link ControllerConfiguration} to the application context.
	 *
	 * @param controllerConfiguration registered aspect
	 */
	protected void addControllerConfiguration(ControllerConfiguration controllerConfiguration) {
		requireNonNull(controllerConfiguration, "A registered controller configuration cannot be null.");
		controllerConfigurations.add(controllerConfiguration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Collection<Aspect> getAspects() {
		return aspects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Collection<Reader> getReaders() {
		return readers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Collection<Writer> getWriters() {
		return writers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return exceptionConfigurations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Collection<ControllerConfiguration> getControllerConfiguration() {
		return controllerConfigurations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationContext initialize(Object applicationConfig) {
		throw new UnsupportedOperationException("Non-DI Configurer does not support this method.");
	}

}
