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
package org.joyrest.routing.resolver;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;

/**
 * Implementation of this class resolve one route's definition which corresponds to the incoming provider.
 *
 * @see DefaultRouteResolver
 * @author pbouda
 */
@FunctionalInterface
public interface RouteResolver {

    /**
     * Find a route definition which corresponds to the incoming provider
     *
     * @param request incoming provider object
     * @return route definition that matches all conditions defined in a child class
     */
    InternalRoute resolveRoute(InternalRequest<?> request);

}
