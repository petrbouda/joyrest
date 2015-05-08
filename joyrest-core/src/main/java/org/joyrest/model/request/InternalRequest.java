package org.joyrest.model.request;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.http.PathParam;
import org.joyrest.utils.PathUtils;

public class InternalRequest<E> implements Request<E> {

	protected Map<HeaderName, String> headers;

	protected Map<String, PathParam> pathParams;

	protected Map<String, String[]> queryParams;

	protected MediaType matchedAccept;

	protected Optional<MediaType> contentType;

	protected List<String> pathParts;

	protected String path;

	protected HttpMethod method;

	protected E entity;

	protected InputStream inputStream;

	@Override
	public Optional<String> getHeader(HeaderName name) {
		requireNonNull(name, "Cannot be retrieved null header.");
		return Optional.ofNullable(getHeaders().get(name));
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		return headers == null ? new HashMap<>() : headers;
	}

	public void setHeaders(Map<HeaderName, String> headers) {
		this.headers = headers;
	}

	@Override
	public Map<String, PathParam> getPathParams() {
		return pathParams == null ? new HashMap<>() : pathParams;
	}

	public void setPathParams(Map<String, PathParam> pathParams) {
		this.pathParams = pathParams;
	}

	@Override
	public String getPathParam(String name) {
		return pathParams.get(name).getValue();
	}

	@Override
	public Map<String, String[]> getQueryParams() {
		return queryParams == null ? new HashMap<>() : queryParams;
	}

	public void setQueryParams(Map<String, String[]> queryParams) {
		this.queryParams = queryParams;
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

	public void setPathParts(List<String> pathParts) {
		this.pathParts = pathParts;
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
		this.pathParts = PathUtils.createPathParts(path);
		this.path = path;
	}

	@Override
	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public MediaType getMatchedAccept() {
		return matchedAccept;
	}

	public void setMatchedAccept(MediaType matchedAccept) {
		this.matchedAccept = matchedAccept;
	}

	@Override
	public Optional<MediaType> getContentType() {
		if(isNull(contentType))
			contentType = getHeader(HeaderName.CONTENT_TYPE)
				.map(MediaType::of);
		return contentType;
	}

	public void setContentType(MediaType contentType) {
		this.contentType = Optional.ofNullable(contentType);
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

	@Override
	public String toString() {
		try {
			return "InternalRequest{" +
				"headers=" + headers +
				", pathParts=" + pathParts +
				", path='" + path + '\'' +
				", method=" + method +
				", inputStreamAvailable=" + (inputStream.available() > 0) +
				'}';
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}