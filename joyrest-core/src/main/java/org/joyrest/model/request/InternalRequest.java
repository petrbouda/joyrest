/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 *
 *
 * @param <E>
 * @see ImmutableRequest
 * @see Request
 * @author pbouda
 */
public abstract class InternalRequest<E> implements Request<E> {

	protected Map<String, PathParam> pathParams;

	protected MediaType matchedAccept;

	protected List<String> pathParts;

	protected E entity;

	/**
	 * Returns a content-type header value
	 *
     * @return content-type header value in {@link Optional} object
	 */
	public abstract Optional<MediaType> getContentType();

	/**
	 * Returns a accept header value
	 *
	 * @return accept header value in {@link Optional} object
	 */
	public abstract Optional<List<MediaType>> getAccept();

	/**
	 * Returns an inputstream object of an incoming entity
	 *
	 * @return incoming entity's input stream
	 */
	public abstract InputStream getInputStream();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, PathParam> getPathParams() {
		return pathParams == null ? new HashMap<>() : pathParams;
	}

	public void setPathParams(Map<String, PathParam> pathParams) {
		this.pathParams = pathParams;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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