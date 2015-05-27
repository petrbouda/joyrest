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
package org.joyrest.routing;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.Type;
import org.joyrest.utils.PathUtils;

/**
 * <p>
 * Class {@link AbstractControllerConfiguration} is abstract implementation of {@link ControllerConfiguration} and makes easier to create
 * the given route using predefined protected method.
 * </p>
 * <p>
 * It can be considered as container for routes which are provided to {@link RequestProcessor} because of processing and handling incoming
 * requests.
 * </p>
 *
 * @author pbouda
 */
public abstract class AbstractControllerConfiguration implements ControllerConfiguration {

	/* Set of routes which are configured in an inherited class */
	private final Set<InternalRoute> routes = new HashSet<>();

	/* Class validates and customized given path */
	private final PathCorrector pathCorrector = new PathCorrector();

	/* Resource path that will be added to the beginning of all routes defined in the inherited class */
	private String controllerPath = null;

	/* RoutingConfiguration's initialization should be executed only once */
	private boolean isInitialized = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void initialize() {
		if (!this.isInitialized) {
			configure();

			List<RoutePart<String>> globalParts = PathUtils.createRoutePathParts(controllerPath);
			if (nonNull(controllerPath))
				this.routes.stream()
					.forEach(route -> route.addControllerPath(globalParts));

			this.isInitialized = true;
		}
	}

	/**
	 * Convenient place where is possible to configure new routes for this instance of {@link ControllerConfiguration}
	 */
	abstract protected void configure();

	/**
	 * Creates a resource part of the path unified for all routes defined in the inherited class
	 *
	 * @param path resource path of all defined class
	 * @throws NullPointerException whether {@code path} is {@code null}
	 */
	protected final void setControllerPath(String path) {
		requireNonNull(path, "Global path cannot be change to 'null'");

		if (!"".equals(path) && !"/".equals(path)) {
			this.controllerPath = pathCorrector.apply(path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<InternalRoute> getRoutes() {
		return routes;
	}

	protected InternalRoute createEntityRoute(HttpMethod method, String path, RouteAction action,
											  Type<?> reqClazz, Type<?> respClazz) {
		requireNonNull(path, "Route path cannot be null.");

		final String correctPath = pathCorrector.apply(path);
		final InternalRoute route = new InternalRoute(correctPath, method, action, reqClazz, respClazz);
		routes.add(route);
		return route;
	}
}