package org.joyrest.servlet.model;

import static java.util.Collections.list;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.common.annotation.UnmodifiableMapCollector.toUnmodifiableMap;
import static org.joyrest.utils.RequestResponseUtils.createPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;

public class ServletRequestWrapper extends InternalRequest<Object> {

	private final HttpServletRequest request;

	private final HttpMethod method;

	private final String path;

	private Map<HeaderName, String> headers;

	protected Optional<MediaType> contentType;

	protected Optional<List<MediaType>> accept;

	public ServletRequestWrapper(HttpServletRequest request) {
		this.request = request;
		this.method = HttpMethod.of(request.getMethod());
		this.path = createPath(request.getRequestURI(), request.getContextPath());
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		if (nonNull(headers))
			return headers;

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
	public Optional<MediaType> getContentType() {
		if (isNull(contentType))
			contentType = getHeader(HeaderName.CONTENT_TYPE)
				.map(MediaType::of);
		return contentType;
	}

	@Override
	public Optional<List<MediaType>> getAccept() {
		if (isNull(accept))
			accept = getHeader(HeaderName.ACCEPT)
				.map(MediaType::list);
		return accept;
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
