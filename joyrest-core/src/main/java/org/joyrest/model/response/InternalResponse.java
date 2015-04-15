package org.joyrest.model.response;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public class InternalResponse<E> implements Response<E> {

	private Map<HeaderName, String> headers = new HashMap<>();

	private HttpStatus status = HttpStatus.OK;

	private E entity;

	private OutputStream responseBody;

	@Override
	public InternalResponse<E> header(HeaderName name, String value) {
		headers.put(name, value);
		return this;
	}

	@Override
	public InternalResponse<E> status(HttpStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public InternalResponse<E> entity(E entity) {
		this.entity = entity;
		return this;
	}

	public OutputStream getOutputStream() {
		return responseBody;
	}

	public void setOutputStream(OutputStream responseBody) {
		this.responseBody = responseBody;
	}

	public Map<HeaderName, String> getHeaders() {
		return headers;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Optional<E> getEntity() {
		return Optional.ofNullable(entity);
	}

}
