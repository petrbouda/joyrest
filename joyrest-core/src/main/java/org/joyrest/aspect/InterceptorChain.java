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
import org.joyrest.routing.InternalRoute;

/**
 * An interface whose implementation {@link InterceptorChainImpl} keeps all information about a chain of the {@link Interceptor aspects}
 * which belongs to given {@link org.joyrest.routing.Route}.
 *
 * @see Interceptor
 * @see InterceptorChainImpl
 * @see GlobalInterceptor
 * @author pbouda
 */
public interface InterceptorChain {

	/**
	 * Method which wraps the call of another interceptor in the chain or in the case that the interceptor is the last one in the chain is
	 * called {@link org.joyrest.routing.RouteAction}.
	 *
	 * @param request provider injected into interceptor
	 * @param response response injected into interceptor
	 * @return temporal value of response
	 */
	InternalResponse<Object> proceed(InternalRequest<Object> request, InternalResponse<Object> response);

	/**
	 * {@link org.joyrest.routing.Route} object which belongs into the given chain
	 *
	 * @return route that owns the current call of {@link InterceptorChain}
	 */
	InternalRoute getRoute();

}
