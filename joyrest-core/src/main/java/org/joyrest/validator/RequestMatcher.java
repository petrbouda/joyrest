package org.joyrest.validator;

import static java.util.stream.Collectors.toList;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;
import static org.joyrest.model.http.MediaType.WILDCARD;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;

public final class RequestMatcher {

	private RequestMatcher() {
	}

	public static boolean matchAccept(InternalRoute route, InternalRequest<?> request) {
		Optional<String> optAccept = request.getHeader(ACCEPT);

		if (optAccept.isPresent()) {
			List<MediaType> acceptTypes = Arrays.stream(optAccept.get().split(","))
				.filter(Objects::nonNull)
				.map(String::trim)
				.map(MediaType::of)
				.distinct()
				.filter(accept -> route.getProduces().contains(accept))
				.collect(toList());

			if (acceptTypes.isEmpty()) {
				return false;
			} else {
				if (acceptTypes.size() == 1) {
					request.setMatchedAccept(acceptTypes.get(0));
				} else {
					// If there are more than one accept media-type and the incoming request contains also
					// content type, so choose the same one media-type
					request.setMatchedAccept(acceptTypes.get(0));
					request.getHeader(HeaderName.CONTENT_TYPE).ifPresent(contentTypeStr -> {
						MediaType contentType = MediaType.of(contentTypeStr);
						if (acceptTypes.contains(contentType))
							request.setMatchedAccept(contentType);
					});
				}

				return true;
			}
		} else {
			return route.getProduces().contains(WILDCARD);
		}
	}

	public static boolean matchContentType(InternalRoute route, InternalRequest<?> request) {
		Optional<String> optContentType = request.getHeader(CONTENT_TYPE);
		MediaType contentType;
		if (!optContentType.isPresent())
			return route.getConsumes().contains(WILDCARD);
		else
			contentType = MediaType.of(optContentType.get());

		if (!route.getConsumes().contains(contentType))
			return false;

		try {
			return request.getInputStream().available() != 0 ^ contentType.equals(MediaType.WILDCARD);
		} catch (IOException e) {
			throw new RuntimeException("An exception occurred during getting a body", e);
		}
	}

	public static boolean matchHttpMethod(InternalRoute route, InternalRequest<?> request) {
		return route.getHttpMethod() == request.getMethod();
	}
}
