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
package org.joyrest.transform.interceptor;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.interceptor.InterceptorInternalOrders;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.SerializationUtils;

/**
 * Aspect which marshalls and unmarshalls an entity object from/to Request/Response object.
 *
 * @author pbouda
 */
public class SerializationInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp)
            throws Exception{

        InternalRoute route = chain.getRoute();
        if (route.hasRequestBody()) {
            Object entity = SerializationUtils.readEntity(route, req);
            req.setEntity(entity);
        }

        chain.proceed(req, resp);
        SerializationUtils.writeEntity(route, req, resp);
        return resp;
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.SERIALIZATION;
    }

}
