package org.joyrest.jetty.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;

public class JettyResponseWrapper extends InternalResponse<Object> {

    private final HttpServletResponse response;

    private final Map<HeaderName, String> headers = new HashMap<>();

    private HttpStatus status;

    public JettyResponseWrapper(HttpServletResponse response) {
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
