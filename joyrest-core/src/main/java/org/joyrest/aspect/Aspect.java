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
package org.joyrest.aspect;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

/**
 * Interface which is needed during defining a new aspect (activity that will be process before and after a route's call)
 *
 * @see AspectChain
 * @see GlobalAspect
 * @author pbouda
 **/
public interface Aspect {

	/**
	 * Method that wraps an actual route call. This method can swallow the call or can call method
	 * {@link AspectChain#proceed(InternalRequest, InternalResponse)} which automatically calls another aspect in chain or
	 * the final step {@link org.joyrest.routing.RouteAction}.
	 *
	 * @param chain keeps all information about all aspects which wraps the route's {@link org.joyrest.routing.RouteAction}
	 * @param request request injected into aspect
	 * @param response response injected into aspect
	 * @return response after the aspect and route call
	 */
	InternalResponse<Object> around(AspectChain chain, InternalRequest<Object> request, InternalResponse<Object> response);

	/**
	 * Order of the route in a {@link AspectChain}.
	 *
	 * @return order in a chain
	 */
	int getOrder();

}
