package org.joyrest.model.request;

import static java.util.Objects.requireNonNull;

import java.io.InputStream;
import java.util.*;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.PathParam;
import org.joyrest.utils.PathUtils;

public class InternalRequest<E> implements Request<E> {

	protected Map<HeaderName, String> headers;

	protected Map<String, PathParam> pathParams;

	protected Map<String, String[]> queryParams;

	protected List<String> pathParts;

	protected String path;

	protected HttpMethod method;

	protected E entity;

	protected InputStream requestBody;

	@Override
	public Optional<String> getHeader(HeaderName name) {
		requireNonNull(name, "Cannot be retrieved null header.");
		return Optional.ofNullable(getHeaders().get(name));
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		return headers == null ? new HashMap<>() : headers;
	}

	@Override
	public Map<String, PathParam> getPathParams() {
		return pathParams == null ? new HashMap<>() : pathParams;
	}

	@Override
	public Optional<String> getPathParam(String name) {
		String value = pathParams.get(name).getValue();
		return Optional.ofNullable(value);
	}

	@Override
	public Map<String, String[]> getQueryParams() {
		return queryParams == null ? new HashMap<>() : queryParams;
	}

	@Override
	public Optional<String[]> getQueryParams(String name) {
		String[] values = queryParams.get(name);
		return Optional.ofNullable(values);
	}

	@Override
	public List<String> getPathParts() {
		return pathParts;
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Optional<E> getEntity() {
		return Optional.ofNullable(entity);
	}

	public InputStream getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(InputStream requestBody) {
		this.requestBody = requestBody;
	}

	public void setHeaders(Map<HeaderName, String> headers) {
		this.headers = headers;
	}

	public void setPathParams(Map<String, PathParam> pathParams) {
		this.pathParams = pathParams;
	}

	public void setQueryParams(Map<String, String[]> queryParams) {
		this.queryParams = queryParams;
	}

	public void setPath(String path) {
		this.pathParts = PathUtils.createPathParts(path);
		this.path = path;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(headers, pathParams, queryParams, path, method, entity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final InternalRequest other = (InternalRequest) obj;
		return Objects.equals(this.headers, other.headers)
				&& Objects.equals(this.pathParams, other.pathParams)
				&& Objects.equals(this.queryParams, other.queryParams)
				&& Objects.equals(this.path, other.path)
				&& Objects.equals(this.method, other.method)
				&& Objects.equals(this.entity, other.entity);
	}
}