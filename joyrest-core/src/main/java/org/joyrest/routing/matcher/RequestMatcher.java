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
package org.joyrest.routing.matcher;

import static java.util.stream.Collectors.toList;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;
import static org.joyrest.model.http.MediaType.WILDCARD;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;

/**
 * Helper utility class for matching an incoming requests against a route configuration
 *
 * @see org.joyrest.processor.RequestProcessor
 * @author pbouda
 */
public final class RequestMatcher {

	private RequestMatcher() {
	}

	/**
	 * Matches route produces configuration and Accept-header in an incoming request
	 *
	 * @param route route configuration
	 * @param request incoming request object
	 * @return returns {@code true} if the given route has produces Media-Type one of an Accept from an incoming request
	 */
	public static boolean matchProduces(InternalRoute route, InternalRequest<?> request) {
		if(route.getProduces().contains(WILDCARD))
			return true;

		Optional<List<MediaType>> optAccept = request.getAccept();
		if (optAccept.isPresent()) {
			List<MediaType> acceptTypes = getAcceptMediaTypes(route, optAccept);

			if (!acceptTypes.isEmpty()) {
				if (acceptTypes.size() == 1) {
					request.setMatchedAccept(acceptTypes.get(0));
				} else {
					// If there are more than one accept media-type and the incoming model contains also
					// content type, so choose the same one media-type
					request.setMatchedAccept(acceptTypes.get(0));
					request.getContentType().ifPresent(contentTypeStr -> {
						MediaType contentType = contentTypeStr;
						if (acceptTypes.contains(contentType))
							request.setMatchedAccept(contentType);
					});
				}

				return true;
			}
		}

		return false;
	}

	private static List<MediaType> getAcceptMediaTypes(InternalRoute route, Optional<List<MediaType>> optAccept) {
		return optAccept.get().stream()
			.filter(accept -> route.getProduces().contains(accept))
			.collect(toList());
	}

	/**
	 * Matches route consumes configuration and Content-Type header in an incoming request
	 *
	 * @param route route configuration
	 * @param request incoming request object
	 * @return returns {@code true} if the given route has consumes Media-Type one of a Content-Type from an incoming request
	 */
	public static boolean matchConsumes(InternalRoute route, InternalRequest<?> request) {
		if(route.getConsumes().contains(WILDCARD))
			return true;

		return request.getContentType()
			.filter(Objects::nonNull)
			.filter(route.getConsumes()::contains)
			.isPresent();
	}

	/**
	 * Matches route an http method in an incoming request
	 *
	 * @param route route configuration
	 * @param request incoming request object
	 * @return returns {@code true} if the given route has the same http method as an incoming request
	 */
	public static boolean matchHttpMethod(InternalRoute route, InternalRequest<?> request) {
		return route.getHttpMethod() == request.getMethod();
	}
}
