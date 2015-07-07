package org.joyrest.stubs;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

public class ResponseStub extends InternalResponse<Object> {

    private Map<HeaderName, String> headers = new HashMap<>();

    private HttpStatus status;

    private OutputStream outputStream;

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<HeaderName, String> headers) {
        this.headers = headers;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public Response<Object> header(HeaderName name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public Response<Object> status(HttpStatus status) {
        this.status = status;
        return this;
    }
}
