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
package org.joyrest.stubs;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;

public class RequestStub extends InternalRequest<Object> {

    private HttpMethod method;

    private String path;

    private Map<HeaderName, String> headers = new HashMap<>();

    private InputStream inputStream;

    private Map<String, String[]> queryParams = new HashMap<>();

    @Override
    public String getRemoteAddr() {
        return "localhost";
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<HeaderName, String> headers) {
        this.headers = headers;
    }

    @Override
    public Optional<String> getHeader(HeaderName name) {
        return Optional.ofNullable(headers.get(name));
    }

    @Override
    public Map<String, String[]> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String[]> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public Optional<String[]> getQueryParams(String name) {
        return Optional.ofNullable(queryParams.get(name));
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public void setAccept(List<MediaType> accept) {
        this.accept = accept;
    }
}
