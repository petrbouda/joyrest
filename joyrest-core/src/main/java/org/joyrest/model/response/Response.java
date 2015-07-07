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
package org.joyrest.model.response;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

/**
 * Response's object abstraction shows which method are available in API in {@link org.joyrest.routing.RouteAction}.
 *
 * @param <E> type of a response object
 * @see InternalResponse
 * @author pbouda
 */
public interface Response<E> {

    /**
     * Adds a new header to a response object
     *
     * @param name header's name
     * @param value header's value
     * @return current object
     */
    Response<E> header(HeaderName name, String value);

    /**
     * Adds a new header to a response object
     *
     * @param name header's name
     * @param value header's value
     * @return current object
     */
    Response<E> header(String name, String value);

    /**
     * Adds a status value to a response object
     *
     * @param status http status code
     * @return current object
     */
    Response<E> status(HttpStatus status);

    /**
     * Adds an entity to a response object
     *
     * @param entity entity object
     * @return current object
     */
    Response<E> entity(E entity);

}