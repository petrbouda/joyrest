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
package org.joyrest.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.Writer;

import static java.util.Objects.requireNonNull;

/**
 * Internal representation of an exception handler.
 *
 * @see ExceptionHandler
 * @see ExceptionHandlerAction
 * @author pbouda
 */
public class InternalExceptionHandler implements ExceptionHandler {

    @SuppressWarnings({"unchecked", "rawtypes"})
    private final ExceptionHandlerAction action;

    private final Type<?> responseType;

    private final Class<? extends Exception> exceptionClass;

    private Map<MediaType, Writer> writers = new HashMap<>();

    public <T extends Exception, RESP> InternalExceptionHandler(Class<T> clazz, ExceptionHandlerAction<RESP, T> action) {
        this(clazz, action, null);
    }

    public <T extends Exception, RESP> InternalExceptionHandler(Class<T> clazz,
                                                                ExceptionHandlerAction<RESP, T> action, Type<RESP> resp) {
        this.exceptionClass = clazz;
        this.action = action;
        this.responseType = resp;
    }

    public Class<? extends Exception> getExceptionClass() {
        return exceptionClass;
    }

    public Type<?> getResponseType() {
        return responseType;
    }

    public void addWriter(Writer writer) {
        requireNonNull(writer);
        this.writers.put(writer.getMediaType(), writer);
    }

    public Optional<Writer> getWriter(MediaType mediaType) {
        return Optional.ofNullable(writers.get(mediaType));
    }

    public Map<MediaType, Writer> getWriters() {
        return writers;
    }

    @SuppressWarnings("unchecked")
    public <T extends Exception> InternalResponse<?> execute(InternalRequest<?> request, InternalResponse<?> response, T ex) {
        action.perform(ImmutableRequest.of(request), response, ex);
        return response;
    }

}
