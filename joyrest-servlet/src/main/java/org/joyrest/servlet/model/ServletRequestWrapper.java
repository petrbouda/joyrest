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
package org.joyrest.servlet.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;
import static org.joyrest.common.collection.UnmodifiableMapCollector.toUnmodifiableMap;

import static java.util.Collections.list;
import static java.util.Objects.nonNull;

public class ServletRequestWrapper extends InternalRequest<Object> {

    private final HttpServletRequest request;

    private final HttpMethod method;

    private final String path;

    private Map<HeaderName, String> headers;

    public ServletRequestWrapper(HttpServletRequest request) {
        this.request = request;
        this.method = HttpMethod.of(request.getMethod());
        this.path = createPath(request.getRequestURI(), request.getContextPath(), request.getServletPath());
    }

    private static String createPath(String requestUri, String contextPath, String servletPath) {
        return requestUri.substring(contextPath.length())
            .substring(servletPath.length());
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        if (nonNull(headers)) {
            return headers;
        }

        headers = StreamSupport.stream(list(request.getHeaderNames()).spliterator(), false)
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
        try {
            return request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred during getting a Jetty InputStream");
        }
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
