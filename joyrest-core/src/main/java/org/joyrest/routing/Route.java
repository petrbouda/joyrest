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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.security.Role;

/**
 * Route definition which contains all needed information about processing an incoming provider.
 *
 * @see RouteAction
 * @see InternalRoute
 * @author pbouda
 */
public interface Route {

    /**
     * Adds an interceptor to the route definition
     *
     * @param interceptor added interceptor
     * @return current route
     */
    Route interceptor(Interceptor... interceptor);

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

	/**
	 * Adds roles which protect a given route
	 *
	 * @param roles roles which protect a route
	 * @return current route
	 */
	Route roles(String... roles);

}
