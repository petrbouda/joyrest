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
package org.joyrest.routing.interceptor;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.interceptor.InterceptorInternalOrders;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import static org.joyrest.utils.PathUtils.getPathParams;

public class PathParamProcessingInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp)
            throws Exception {

        req.setPathParams(getPathParams(chain.getRoute(), req.getPathParts()));
        return chain.proceed(req, resp);
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.PATH_PARAM_PROCESSING;
    }
}
