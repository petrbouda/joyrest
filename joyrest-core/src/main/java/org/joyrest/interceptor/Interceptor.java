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
package org.joyrest.interceptor;

import org.joyrest.common.annotation.Ordered;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

/**
 * Interface which is needed during defining a new interceptor (activity that will be process before and after a route's call)
 *
 * @see InterceptorChain
 * @see GlobalInterceptor
 * @author pbouda
 **/
public interface Interceptor extends Ordered {

    /**
     * Method that wraps an actual route call. This method can swallow the call or can call method
     * {@link InterceptorChain#proceed(InternalRequest, InternalResponse)} which automatically calls another interceptor in
     * chain or
     * the final step {@link org.joyrest.routing.RouteAction}.
     *
     * @param chain keeps all information about all aspects which wraps the route's {@link org.joyrest.routing.RouteAction}
     * @param req provider injected into interceptor
     * @param resp response injected into interceptor
     * @return response after the interceptor and route call
     */
    InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp)
        throws Exception;

}
