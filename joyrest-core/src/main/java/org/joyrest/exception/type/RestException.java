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
package org.joyrest.exception.type;

import static java.util.Collections.emptyMap;

import java.util.Map;
import java.util.function.Supplier;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 3036905776427215166L;

	private final HttpStatus status;

	private final String message;

	private final Map<HeaderName, String> headers;

	public RestException(HttpStatus status) {
		this(status, null, emptyMap());
	}

	public RestException(HttpStatus status, String message) {
		this(status, message, emptyMap());
	}

	public RestException(HttpStatus status, String message, Throwable e) {
		super(message, e);
		this.status = status;
		this.message = message;
		this.headers = emptyMap();
	}

	public RestException(HttpStatus status, String message, Map<HeaderName, String> headers) {
		this.status = status;
		this.message = message;
		this.headers = headers;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Map<HeaderName, String> getHeaders() {
		return headers;
	}

	public static Supplier<RestException> badRequestSupplier(String msg) {
		return () -> new RestException(HttpStatus.BAD_REQUEST, msg);
	}

	public static Supplier<RestException> notFoundSupplier(String msg) {
		return () -> new RestException(HttpStatus.NOT_FOUND, msg);
	}

	public static Supplier<RestException> unsupportedMediaTypeSupplier(String msg) {
		return () -> new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, msg);
	}

	public static Supplier<RestException> notAcceptableSupplier(String msg) {
		return () -> new RestException(HttpStatus.NOT_ACCEPTABLE, msg);
	}

	public static Supplier<RestException> internalServerErrorSupplier(String msg) {
		return () -> new RestException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}

}
