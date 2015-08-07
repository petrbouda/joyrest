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
package org.joyrest.interceptor.aspects;

import java.util.Map;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class SwallowInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain,
                                           InternalRequest<Object> request,
                                           InternalResponse<Object> response) throws Exception {
        Map<HeaderName, String> headers = request.getHeaders();

        headers.put(HeaderName.of("Swallowed"), "YES");

        return response;
    }

    @Override
    public int getOrder() {
        return -50;
    }
}
