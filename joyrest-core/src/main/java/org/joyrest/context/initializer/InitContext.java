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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joyrest.aspect.Interceptor;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class InitContext {

	------> NENI TO POTREBA!!

//	private final Set<Reader> readers;
//
//	private final Set<Writer> writers;
//
//	private final Set<Interceptor> interceptors;
//
//	private final Set<ControllerConfiguration> controllerConfigurations;

	private final Map<Class<? extends Exception>, InternalExceptionHandler> exceptionConfigurations;

	private final Set<InternalRoute> routes;

	private InitContext(
			Set<Reader> readers,
			Set<Writer> writers,
			Set<Interceptor> interceptors,
			Set<ControllerConfiguration> controllerConfigurations,
			Map<Class<? extends Exception>, InternalExceptionHandler> exceptionConfigurations,
			Set<InternalRoute> routes) {

		this.readers = readers;
		this.writers = writers;
		this.interceptors = interceptors;
		this.exceptionConfigurations = exceptionConfigurations;
		this.controllerConfigurations = controllerConfigurations;
		this.routes = routes;
	}

	public Set<Reader> getReaders() {
		return readers;
	}

	public Set<Writer> getWriters() {
		return writers;
	}

	public Set<Interceptor> getInterceptors() {
		return interceptors;
	}

	public Set<ControllerConfiguration> getControllerConfigurations() {
		return controllerConfigurations;
	}

	public Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionConfigurations() {
		return exceptionConfigurations;
	}

	public Set<InternalRoute> getRoutes() {
		return routes;
	}

	public final static class Builder {

		private final Set<Reader> readers = new HashSet<>();

		private final Set<Writer> writers = new HashSet<>();

		private final Set<Interceptor> interceptors = new HashSet<>();

		private final Set<ControllerConfiguration> controllerConfigurations = new HashSet<>();

		private final Map<Class<? extends Exception>, InternalExceptionHandler> exceptionConfigurations = new HashMap<>();

		private final Set<InternalRoute> routes = new HashSet<>();

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

		public Builder exceptionConfigurations(Class<? extends Exception> clazz, InternalExceptionHandler handler) {
			requireNonNull(exceptionConfigurations);
			this.exceptionConfigurations.addAll(asList(exceptionConfigurations));
			return this;
		}

		public Builder controllerConfigurations(ControllerConfiguration... controllerConfigurations) {
			requireNonNull(controllerConfigurations);
			this.controllerConfigurations.addAll(asList(controllerConfigurations));
			return this;
		}

		public Builder routes(InternalRoute... routes) {
			requireNonNull(routes);
			this.routes.addAll(asList(routes));
			return this;
		}

		public InitContext build() {
			return new InitContext(readers, writers, interceptors, controllerConfigurations, exceptionConfigurations, routes);
		}
	}
}
