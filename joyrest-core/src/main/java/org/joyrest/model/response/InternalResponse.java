package org.joyrest.model.response;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public abstract class InternalResponse<E> implements Response<E> {

	private E entity;

	public abstract OutputStream getOutputStream();

	public abstract Map<HeaderName, String> getHeaders();

	public abstract HttpStatus getStatus();

	@Override
	public InternalResponse<E> entity(E entity) {
		this.entity = entity;
		return this;
	}

	public Optional<E> getEntity() {
		return Optional.ofNullable(entity);
	}

}
