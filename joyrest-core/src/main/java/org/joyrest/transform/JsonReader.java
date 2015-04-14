package org.joyrest.transform;

import java.io.IOException;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;
import org.joyrest.routing.entity.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader implements Reader {

	private final MediaType supportedMediaType = MediaType.JSON;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T readFrom(InternalRequest<T> request, Type<T> type) {
		try {
			return mapper.readValue(request.getRequestBody(), (Class<T>) type.getType());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during unmarshalling from JSON.");
		}
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}

	@Override
	public boolean isCompatible(Route<?, ?> route) {
		return route.getConsumes().contains(supportedMediaType);
	}

	@Override
	public boolean isGeneral() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof JsonReader))
			return false;

		JsonReader that = (JsonReader) o;

		return !(supportedMediaType != null ?
			!supportedMediaType.equals(that.supportedMediaType) : that.supportedMediaType != null);
	}

	@Override
	public int hashCode() {
		return supportedMediaType != null ? supportedMediaType.hashCode() : 0;
	}
}
