/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.context.initializer;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.joyrest.aspect.Interceptor;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class InitContext {

	private final Map<Class<?>, Collection<?>> map;

	private final Collection<Reader> readers;

	private final Collection<Writer> writers;

	private final Collection<Interceptor> interceptors;

	private final Collection<ExceptionConfiguration> exceptionConfigurations;

	private final Collection<ControllerConfiguration> controllerConfigurations;

	private InitContext(
		Collection<Reader> readers,
		Collection<Writer> writers,
		Collection<Interceptor> interceptors,
		Collection<ExceptionConfiguration> exceptionConfigurations,
		Collection<ControllerConfiguration> controllerConfigurations) {

		this.readers = readers;
		this.writers = writers;
		this.interceptors = interceptors;
		this.exceptionConfigurations = exceptionConfigurations;
		this.controllerConfigurations = controllerConfigurations;

		this.map = new HashMap<>();
		this.map.put(Reader.class, readers);
		this.map.put(Writer.class, writers);
		this.map.put(Interceptor.class, interceptors);
		this.map.put(ExceptionConfiguration.class, exceptionConfigurations);
		this.map.put(ControllerConfiguration.class, controllerConfigurations);
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> getBeans(Class<T> clazz) {
		return (Collection<T>) this.map.get(clazz);
	}

	public Collection<Reader> getReaders() {
		return readers;
	}

	public Collection<Writer> getWriters() {
		return writers;
	}

	public Collection<Interceptor> getInterceptors() {
		return interceptors;
	}

	public Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return exceptionConfigurations;
	}

	public Collection<ControllerConfiguration> getControllerConfigurations() {
		return controllerConfigurations;
	}

	public final static class Builder {

		private final Collection<Reader> readers = new HashSet<>();

		private final Collection<Writer> writers = new HashSet<>();

		private final Collection<Interceptor> interceptors = new HashSet<>();

		private final Collection<ExceptionConfiguration> exceptionConfigurations = new HashSet<>();

		private final Collection<ControllerConfiguration> controllerConfigurations = new HashSet<>();

		public Builder readers(Reader... readers) {
			requireNonNull(readers);
			this.readers.addAll(asList(readers));
			return this;
		}

		public Builder writers(Writer... writers) {
			requireNonNull(writers);
			this.writers.addAll(asList(writers));
			return this;
		}

		public Builder interceptors(Interceptor... interceptors) {
			requireNonNull(interceptors);
			this.interceptors.addAll(asList(interceptors));
			return this;
		}

		public Builder exceptionConfigurations(ExceptionConfiguration... exceptionConfigurations) {
			requireNonNull(exceptionConfigurations);
			this.exceptionConfigurations.addAll(asList(exceptionConfigurations));
			return this;
		}

		public Builder controllerConfigurations(ControllerConfiguration... controllerConfigurations) {
			requireNonNull(controllerConfigurations);
			this.controllerConfigurations.addAll(asList(controllerConfigurations));
			return this;
		}

		public InitContext build() {
			return new InitContext(readers, writers, interceptors, exceptionConfigurations, controllerConfigurations);
		}
	}
}
