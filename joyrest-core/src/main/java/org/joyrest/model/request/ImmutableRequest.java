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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.PathParam;

/**
 * {@inheritDoc}
 */
public final class ImmutableRequest<E> implements Request<E> {

	private final Request<E> request;

	private ImmutableRequest(Request<E> request) {
		this.request = request;
	}

	/**
	 * Creates immutable version of original {@link Request} object
	 *
	 * @param request the original request object
	 * @param <T> type of an entity in request object
	 * @return immutable request
	 */
	public static <T> ImmutableRequest<T> of(Request<T> request) {
		return new ImmutableRequest<>(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<String> getHeader(HeaderName name) {
		return request.getHeader(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<HeaderName, String> getHeaders() {
		return request.getHeaders();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, PathParam> getPathParams() {
		return request.getPathParams();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPathParam(String name) {
		return request.getPathParam(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<String[]> getQueryParams(String name) {
		return request.getQueryParams(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String[]> getQueryParams() {
		return request.getQueryParams();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpMethod getMethod() {
		return request.getMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return request.getPath();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E getEntity() {
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
		final ImmutableRequest<?> other = (ImmutableRequest<?>) obj;
		return Objects.equals(this.request, other.request);
	}
}