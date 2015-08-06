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
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import static org.joyrest.exception.type.RestException.notAcceptableSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

/**
 * Aspect which marshalls and unmarshalls an entity object from/to Request/Response object.
 *
 * @author pbouda
 */
public class SerializationInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp) {
        InternalRoute route = chain.getRoute();
        if (route.hasRequestBody()) {
            Object entity = readEntity(route, req);
            req.setEntity(entity);
        }

        chain.proceed(req, resp);
        writeEntity(route, req, resp);
        return resp;
    }

    private void writeEntity(InternalRoute route, InternalRequest<?> request, InternalResponse<?> response) {
        if (response.getEntity().isPresent()) {
            MediaType accept = request.getMatchedAccept();
            Writer writer = route.getWriter(accept)
                .orElseThrow(notAcceptableSupplier(String.format(
                    "No suitable Writer for accept header [%s] is registered.", accept)));
            response.header(HeaderName.CONTENT_TYPE, accept.get());
            writer.writeTo(response, request);
        }
    }

    private Object readEntity(InternalRoute route, InternalRequest<Object> request) {
        MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
        Reader reader = route.getReader(contentType)
            .orElseThrow(unsupportedMediaTypeSupplier(String.format(
                "No suitable Reader for content-type header [%s] is registered.", contentType)));
        return reader.readFrom(request, route.getRequestType());
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.SERIALIZATION;
    }

}
