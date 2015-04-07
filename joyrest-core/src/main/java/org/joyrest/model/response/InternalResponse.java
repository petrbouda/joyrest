package org.joyrest.model.response;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InternalResponse implements Response {

    private Map<HeaderName, String> headers = new HashMap<>();

    private HttpStatus status = HttpStatus.OK;

    private Object entity;

    private OutputStream responseBody;

    @Override
    public InternalResponse header(HeaderName name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public InternalResponse status(HttpStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public InternalResponse entity(Object entity) {
        this.entity = entity;
        return this;
    }

    public void setOutputStream(OutputStream responseBody) {
        this.responseBody = responseBody;
    }

    public OutputStream getOutputStream() {
        return responseBody;
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
    public Optional<Object> getEntity() {
        return Optional.ofNullable(entity);
    }

}
