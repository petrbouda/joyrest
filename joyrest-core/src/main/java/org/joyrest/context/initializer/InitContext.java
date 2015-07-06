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

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class InitContext {

	private List<Reader> readers = new ArrayList<>();

	private List<Writer> writers = new ArrayList<>();

	private List<Interceptor> interceptors = new ArrayList<>();

	private List<ControllerConfiguration> controllerConfigurations = new ArrayList<>();

	private List<ExceptionConfiguration> exceptionConfigurations = new ArrayList<>();

	private Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers = new HashMap<>();

	private List<InternalRoute> routes = new ArrayList<>();

	public void setExceptionHandlers(Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers) {
		this.exceptionHandlers = exceptionHandlers;
	}

	public Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionHandlers() {
		return exceptionHandlers;
	}

	public void setRoutes(List<InternalRoute> routes) {
		this.routes = routes;
	}

	public List<InternalRoute> getRoutes() {
		return routes;
	}

	public List<Reader> getReaders() {
		return readers;
	}

	public void setReaders(List<Reader> readers) {
		this.readers = readers;
	}

	public List<Writer> getWriters() {
		return writers;
	}

	public void setWriters(List<Writer> writers) {
		this.writers = writers;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

	public List<ControllerConfiguration> getControllerConfigurations() {
		return controllerConfigurations;
	}

	public void setControllerConfigurations(List<ControllerConfiguration> controllerConfigurations) {
		this.controllerConfigurations = controllerConfigurations;
	}

	public List<ExceptionConfiguration> getExceptionConfigurations() {
		return exceptionConfigurations;
	}

	public void setExceptionConfigurations(List<ExceptionConfiguration> exceptionConfigurations) {
		this.exceptionConfigurations = exceptionConfigurations;
	}

	public void addControllerConfiguration(ControllerConfiguration controllerConfiguration) {
		requireNonNull(controllerConfiguration, "Created Controller Configuration added to the context references null value.");
		controllerConfigurations.add(controllerConfiguration);
	}

	public void addExceptionConfiguration(ExceptionConfiguration exceptionConfiguration) {
		requireNonNull(exceptionConfiguration, "Created Exception Configuration added to the context references null value.");
		exceptionConfigurations.add(exceptionConfiguration);
	}

	public void addReader(Reader reader) {
		requireNonNull(reader, "Created Reader added to the context references null value.");
		readers.add(reader);
	}

	public void addWriter(Writer writer) {
		requireNonNull(writer, "Created Writer added to the context references null value.");
		writers.add(writer);
	}

	public void addInterceptor(Interceptor interceptor) {
		requireNonNull(interceptor, "Created Interceptor added to the context references null value.");
		interceptors.add(interceptor);
	}

	public void addRoute(InternalRoute route) {
		requireNonNull(route, "Created Route added to the context references null value.");
		routes.add(route);
	}

	public void addExceptionHandlers(Class<? extends Exception> clazz, InternalExceptionHandler handler) {
		requireNonNull(clazz, "Class of the created exception handler cannot be null.");
		requireNonNull(handler, "Created handler cannot be null.");
		exceptionHandlers.putIfAbsent(clazz, handler);
	}

}
