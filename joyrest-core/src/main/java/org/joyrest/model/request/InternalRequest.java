package org.joyrest.model.request;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static org.joyrest.utils.PathUtils.createPathParts;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.http.PathParam;

public abstract class InternalRequest<E> implements Request<E> {

	protected Map<String, PathParam> pathParams;

	protected MediaType matchedAccept;

	protected List<String> pathParts;

	protected E entity;

	public abstract Optional<MediaType> getContentType();

	public abstract Optional<List<MediaType>> getAccept();

	public abstract InputStream getInputStream();

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

	public List<String> getPathParts() {
		if (isNull(pathParts)) {
			requireNonNull(getPath(), "Path cannot be null in case of a PathPart's creating");
			pathParts = createPathParts(getPath());
		}
		return pathParts;
	}

	@Override
	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public MediaType getMatchedAccept() {
		return matchedAccept;
	}

	public void setMatchedAccept(MediaType matchedAccept) {
		this.matchedAccept = matchedAccept;
	}
}