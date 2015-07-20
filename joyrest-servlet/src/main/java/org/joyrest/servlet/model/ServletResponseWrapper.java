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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;

public class ServletResponseWrapper extends InternalResponse<Object> {

    private final HttpServletResponse response;

    private final Map<HeaderName, String> headers = new HashMap<>();

    private HttpStatus status;

    public ServletResponseWrapper(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public OutputStream getOutputStream() {
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred during getting a Jetty InputStream");
        }
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        return headers;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public org.joyrest.model.response.Response<Object> header(HeaderName name, String value) {
        response.addHeader(name.getValue(), value);
        this.headers.put(name, value);
        return this;
    }

    @Override
    public org.joyrest.model.response.Response<Object> status(HttpStatus status) {
        response.setStatus(status.code());
        this.status = status;
        return this;
    }
}
