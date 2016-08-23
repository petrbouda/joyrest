/*
 * Copyright 2016 Petr Bouda
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
package org.joyrest.transform;

import java.util.Optional;
import java.util.function.Supplier;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

import static org.joyrest.exception.type.RestException.notAcceptableSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

public class SerializationUtils {

    public static void writeEntity(InternalRoute route, InternalRequest<?> request, InternalResponse<?> response) {
        writeEntity(route, request, response, null);
    }

    public static void writeEntity(InternalRoute route, InternalRequest<?> request, InternalResponse<?> response,
                                   MediaType fallback) {
        if (!response.isEntityWritten() && response.getEntity().isPresent()) {
            MediaType accept = request.getMatchedAccept();
            Optional<Writer> optWriter = route.getWriter(accept);

            Writer writer;
            if (!optWriter.isPresent()) {
                Supplier<RestException> restExceptionSupplier = notAcceptableSupplier(
                        String.format("No suitable Writer for accept header [%s] is registered.", accept));

                if (fallback == null) {
                    throw restExceptionSupplier.get();
                } else {
                    writer = route.getWriter(fallback).orElseThrow(restExceptionSupplier);
                    response.header(HeaderName.CONTENT_TYPE, fallback.get());
                }
            } else {
                writer = optWriter.get();
                response.header(HeaderName.CONTENT_TYPE, accept.get());
            }

            writer.writeTo(response, request);
            response.setEntityWritten(true);
        }
    }

    public static Object readEntity(InternalRoute route, InternalRequest<Object> request) {
        MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
        Reader reader = route.getReader(contentType)
                .orElseThrow(unsupportedMediaTypeSupplier(String.format(
                        "No suitable Reader for content-type header [%s] is registered.", contentType)));
        return reader.readFrom(request, route.getRequestType());
    }
}
