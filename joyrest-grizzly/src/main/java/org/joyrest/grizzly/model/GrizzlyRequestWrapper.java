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
package org.joyrest.grizzly.model;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.glassfish.grizzly.http.server.Request;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;
import static org.joyrest.common.collection.UnmodifiableMapCollector.toUnmodifiableMap;

import static java.util.Objects.nonNull;

public class GrizzlyRequestWrapper extends InternalRequest<Object> {

    private final Request request;

    private final HttpMethod method;

    private final String path;

    private Map<HeaderName, String> headers;

    public GrizzlyRequestWrapper(Request request) {
        this.request = request;
        this.method = HttpMethod.of(request.getMethod().getMethodString());
        this.path = request.getRequestURI().substring(request.getContextPath().length());
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        if (nonNull(headers)) {
            return headers;
        }

        headers = StreamSupport.stream(request.getHeaderNames().spliterator(), false)
            .collect(toUnmodifiableMap(HeaderName::of, name -> getHeader(HeaderName.of(name)).get()));
        return headers;
    }

    @Override
    public Optional<String> getHeader(HeaderName name) {
        return Optional.ofNullable(request.getHeader(name.getValue()));
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    @Override
    public InputStream getInputStream() {
        return request.getInputStream();
    }

    @Override
    public Map<String, String[]> getQueryParams() {
        return request.getParameterMap();
    }

    @Override
    public Optional<String[]> getQueryParams(String name) {
        return Optional.ofNullable(request.getParameterValues(name));
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }
}
