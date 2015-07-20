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
package org.joyrest.undertow.model;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;
import static org.joyrest.common.collection.UnmodifiableMapCollector.toUnmodifiableMap;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public class UndertowRequestWrapper extends InternalRequest<Object> {

    private final HttpServerExchange request;

    private final HttpMethod method;

    private final String path;

    private Map<HeaderName, String> headers;

    private Map<String, String[]> queryParams;

    public UndertowRequestWrapper(HttpServerExchange request) {
        this.request = request;
        this.method = HttpMethod.of(request.getRequestMethod().toString());
        this.path = request.getRelativePath();
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        if (isNull(headers)) {
            headers = StreamSupport.stream(request.getRequestHeaders().getHeaderNames().spliterator(), false)
                .map(HttpString::toString)
                .collect(toUnmodifiableMap(HeaderName::of, name -> getHeader(HeaderName.of(name)).get()));
        }
        return headers;
    }

    @Override
    public Optional<String> getHeader(HeaderName name) {
        return Optional.ofNullable(request.getRequestHeaders().getFirst(name.getValue()));
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getRemoteAddr() {
        return request.getHostName();
    }

    @Override
    public InputStream getInputStream() {
        return request.getInputStream();
    }

    @Override
    public Map<String, String[]> getQueryParams() {
        if (isNull(queryParams)) {
            queryParams = request.getQueryParameters().entrySet().stream()
                .collect(toMap(Map.Entry::getKey,
                    entry -> entry.getValue().toArray(new String[entry.getValue().size()])));
        }

        return queryParams;
    }

    @Override
    public Optional<String[]> getQueryParams(String name) {
        return Optional.ofNullable((String[]) request.getQueryParameters().get(name).toArray());
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }
}
