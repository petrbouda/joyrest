package org.joyrest.grizzly.model;

import static java.util.Objects.nonNull;
import static org.joyrest.common.UnmodifiableMapCollector.toUnmodifiableMap;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.glassfish.grizzly.http.server.Request;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;

public class GrizzlyRequestWrapper extends InternalRequest<Object> {

	private final Request request;

	private final HttpMethod method;

	private final String path;

	private Map<HeaderName, String> headers;

	public GrizzlyRequestWrapper(Request request) {
		this.request = request;
		this.method = HttpMethod.of(request.getMethod().getMethodString());
		this.path = request.getRequestURI().substring(request.getContextPath().length());
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		if (nonNull(headers))
			return headers;

		headers = StreamSupport.stream(request.getHeaderNames().spliterator(), false)
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
		return request.getInputStream();
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
