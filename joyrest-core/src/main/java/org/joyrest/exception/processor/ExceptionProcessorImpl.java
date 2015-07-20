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
package org.joyrest.exception.processor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.Writer;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ExceptionProcessorImpl implements ExceptionProcessor {

    private final Map<Class<? extends Exception>, InternalExceptionHandler> handlers;

    public ExceptionProcessorImpl(ApplicationContext config) {
        this.handlers = config.getExceptionHandlers();
    }

    @Override
    public <T extends Exception> void process(T ex, InternalRequest<Object> request, InternalResponse<Object> response)
        throws Exception {
        Class<? extends Exception> clazz = ex.getClass();
        InternalExceptionHandler handler = handlers.get(clazz);

        if (isNull(handler)) {
            handler = getHandlerFromParent(clazz).orElseThrow(() -> ex);
        }

        handler.execute(request, response, ex);
        writeEntity(handler, request, response);
    }

    private void writeEntity(InternalExceptionHandler handler, InternalRequest<?> request, InternalResponse<?> response) {
        if (response.getEntity().isPresent()) {
            Writer writer = null;
            if (nonNull(request.getMatchedAccept())) {
                Optional<Writer> optWriter = handler.getWriter(request.getMatchedAccept());
                if (optWriter.isPresent()) {
                    writer = optWriter.get();
                }
            }

            if (isNull(writer)) {
                writer = chooseWriter(handler, request);
            }

            response.header(CONTENT_TYPE, writer.getMediaType().get());
            writer.writeTo(response, request);
        }
    }

    private Writer chooseWriter(InternalExceptionHandler handler, InternalRequest<?> request) {
        List<MediaType> acceptMediaTypes = request.getAccept();
        return acceptMediaTypes.stream()
            .filter(accept -> handler.getWriter(accept).isPresent())
            .findFirst()
            .flatMap(handler::getWriter)
            .orElseThrow(internalServerErrorSupplier(
                String.format("No writer registered for Accept%s and Exception Response-Type[%s]",
                    acceptMediaTypes, handler.getExceptionClass())));
    }

    private Optional<InternalExceptionHandler> getHandlerFromParent(Class<? extends Exception> clazz) {
        if (clazz == Exception.class) {
            return Optional.empty();
        }

        Class<?> superClazz = clazz.getSuperclass();
        while (superClazz != Exception.class) {
            InternalExceptionHandler handler = handlers.get(superClazz);
            if (nonNull(handler)) {
                return Optional.of(handler);
            }

            superClazz = superClazz.getSuperclass();
        }

        return Optional.empty();
    }
}