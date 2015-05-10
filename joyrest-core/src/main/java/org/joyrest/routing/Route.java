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

import org.joyrest.aspect.Aspect;
import org.joyrest.model.http.MediaType;

/**
 * Route definition which contains all needed information about processing an incoming request.
 *
 * @see RouteAction
 * @see InternalRoute
 * @author pbouda
 */
public interface Route {

	/**
	 * Adds an aspect to the route definition
	 *
	 * @param aspect added aspect
	 * @return current route
	 */
	Route aspect(Aspect... aspect);

	/**
	 * Adds a consumed Media-Type to the route definition
	 *
	 * @param consumes media-type
	 * @return current route
	 */
	Route consumes(MediaType... consumes);

	/**
	 * Adds a produced Media-Type to the route definition
	 *
	 * @param produces media type
	 * @return current route
	 */
	Route produces(MediaType... produces);

}
