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
package org.joyrest.model.http;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpMethod {

	OPTIONS,
	GET,
	HEAD,
	POST,
	PUT,
	DELETE,
	TRACE,
	CONNECT,
	PATCH;

	private static final Map<String, HttpMethod> methods;

	static {
		methods = Arrays.asList(values()).stream()
			.collect(Collectors.toMap(HttpMethod::name, Function.identity()));
	}

	public static final HttpMethod of(String methodName) {
		requireNonNull(methodName, "Method name cannot be null.");
		return methods.get(methodName.toUpperCase());
	}

}