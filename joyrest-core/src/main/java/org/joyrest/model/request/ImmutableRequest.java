package org.joyrest.model.request;

import org.joyrest.model.http.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class ImmutableRequest<E> implements Request<E> {

    private final Request<E> request;

    private ImmutableRequest(Request<E> request) {
        this.request = request;
    }

    public static <T> ImmutableRequest of(Request<T> request) {
        return new ImmutableRequest<>(request);
    }

    @Override
    public Optional<String> getHeader(HeaderName name) {
        return request.getHeader(name);
    }

    @Override
    public Map<HeaderName, String> getHeaders() {
        return request.getHeaders();
    }

    @Override
    public Map<String, PathParam> getPathParams() {
        return request.getPathParams();
    }

    @Override
    public Optional<String> getPathParam(String name) {
        return request.getPathParam(name);
    }

    @Override
    public Optional<String[]> getQueryParams(String name) {
        return request.getQueryParams(name);
    }

    @Override
    public Map<String, String[]> getQueryParams() {
        return request.getQueryParams();
    }

    @Override
    public HttpMethod getMethod() {
        return request.getMethod();
    }

    @Override
    public String getPath() {
        return request.getPath();
    }

    @Override
    public List<String> getPathParts() {
        return request.getPathParts();
    }

    @Override
    public Optional<E> getEntity() {
        return request.getEntity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ImmutableRequest other = (ImmutableRequest) obj;
        return Objects.equals(this.request, other.request);
    }
}