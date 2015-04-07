package org.joyrest.validator;

import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;
import static org.joyrest.model.http.MediaType.WILDCARD;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.routing.Route;

public final class RequestValidator {

	private RequestValidator() {
	}

	public static boolean validateNonEmptyList(Route route) {
		return Objects.nonNull(route);
	}

	public static boolean validateAccept(Route route, InternalRequest request) {
		Optional<String> optAccept = request.getHeader(ACCEPT);

		if (optAccept.isPresent()) {
			MediaType accept = MediaType.of(optAccept.get());
			return route.getProduces().contains(accept);
		} else {
			return route.getProduces().contains(WILDCARD);
		}
	}

	public static boolean validateContentType(Route route, InternalRequest request) {
		Optional<String> optContentType = request.getHeader(CONTENT_TYPE);
		MediaType contentType;
		if (!optContentType.isPresent()) {
			return route.getConsumes().contains(WILDCARD);
		} else {
			contentType = MediaType.of(optContentType.get());
		}

		if (!route.getConsumes().contains(contentType)) {
			return false;
		}

		try {
			return request.getRequestBody().available() != 0;
		} catch (IOException e) {
			throw new RuntimeException("An exception occurred during getting a body", e);
		}
	}

	public static boolean validateHttpMethod(Route route, InternalRequest request) {
		return route.getHttpMethod() == request.getMethod();
	}
}
