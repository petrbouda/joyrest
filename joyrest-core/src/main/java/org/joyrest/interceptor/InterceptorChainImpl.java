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

import java.util.ArrayDeque;
import java.util.Queue;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class InterceptorChainImpl implements InterceptorChain {

    private final Queue<Interceptor> interceptors = new ArrayDeque<>();

    private final InternalRoute route;

    public InterceptorChainImpl(InternalRoute route) {
        requireNonNull(route);
        this.route = route;
        this.interceptors.addAll(route.getInterceptors());
    }

    @Override
    public InternalResponse<Object> proceed(InternalRequest<Object> request, InternalResponse<Object> response) throws Exception {
        requireNonNull(request);
        Interceptor interceptor = interceptors.poll();

        if (nonNull(interceptor)) {
            return interceptor.around(this, request, response);
        }

        return route.execute(request, response);
    }

    @Override
    public InternalRoute getRoute() {
        return route;
    }
}