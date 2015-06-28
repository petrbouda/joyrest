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

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.PathParam;

/**
 * Request's object abstraction shows which method are available in API in {@link org.joyrest.routing.RouteAction}.
 *
 * @param <E> type of a provider object
 * @see InternalRequest
 * @see ImmutableRequest
 * @author pbouda
 */
public interface Request<E> {

	/**
	 * Returns authenticated user
	 *
	 * @return authenticated user
	 **/
	Optional<Principal> getPrincipal();

	/**
	 * Returns all headers in a provider object
	 *
	 * @return all headers
	 */
	Map<HeaderName, String> getHeaders();

	/**
	 * Returns a concrete header according to {@code name} value
	 *
	 * @param name header's name
	 * @return header value in {@link Optional} object
	 */
	Optional<String> getHeader(HeaderName name);

	/**
	 * Returns a concrete header according to {@code name} value
	 *
	 * @param name header's name
	 * @return header value in {@link Optional} object
	 */
	Optional<String> getHeader(String name);

	/**
	 * Returns all query params in a provider object
	 *
	 * @return all query params
	 */
	Map<String, String[]> getQueryParams();

	/**
	 * Returns a concrete query param according to {@code name} value
	 *
	 * @param name query's param name
	 * @return query param value in {@link Optional} object
	 */
	Optional<String[]> getQueryParams(String name);

	/**
	 * Returns all path params in a provider object
	 *
	 * @return all path params
	 */
	Map<String, PathParam> getPathParams();

	/**
	 * Returns a concrete path param according to {@code name} value
	 *
	 * @param name path's param name
	 * @return path param value
	 */
	String getPathParam(String name);

	/**
	 * Returns an http method value
	 *
	 * @return http method
	 */
	HttpMethod getMethod();

	/**
	 * Returns a provider's path value
	 *
	 * @return path value
	 */
	String getPath();

	/**
	 * Returns an incoming entity
	 *
	 * @return entity
	 */
	E getEntity();

}