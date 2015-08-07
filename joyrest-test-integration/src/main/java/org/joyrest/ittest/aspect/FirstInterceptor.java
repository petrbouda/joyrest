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
package org.joyrest.ittest.aspect;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import static org.joyrest.ittest.aspect.ContextHolder.RegistryKey.FIRST_KEY;
import static org.joyrest.ittest.aspect.ContextHolder.RegistryKey.SECOND_KEY;
import static org.joyrest.ittest.aspect.ContextHolder.RegistryKey.THIRD_KEY;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FirstInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain,
                                           InternalRequest<Object> request,
                                           InternalResponse<Object> response) throws Exception {
        if (nonNull(ContextHolder.get(FIRST_KEY)) ||
            nonNull(ContextHolder.get(SECOND_KEY)) ||
            nonNull(ContextHolder.get(THIRD_KEY))) {
            throw new RuntimeException("Fail during Request side in FirstAspect");
        }

        ContextHolder.put(FIRST_KEY, "yes");

        InternalResponse<Object> resp = chain.proceed(request, response);

        if (isNull(ContextHolder.get(FIRST_KEY)) ||
            nonNull(ContextHolder.get(SECOND_KEY)) ||
            nonNull(ContextHolder.get(THIRD_KEY))) {
            throw new RuntimeException("Fail during Response side in FirstAspect");
        }

        resp.header(HeaderName.of("result"), "success");
        return resp;
    }

    @Override
    public int getOrder() {
        return -50;
    }
}
