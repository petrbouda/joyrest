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

import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class InitContext {

	private Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers = new HashMap<>();

	private Set<InternalRoute> routes = new HashSet<>();

	public Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionConfigurations() {
		return exceptionHandlers;
	}

	public void setExceptionHandlers(Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers) {
		this.exceptionHandlers = exceptionHandlers;
	}

	public Set<InternalRoute> getRoutes() {
		return routes;
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
