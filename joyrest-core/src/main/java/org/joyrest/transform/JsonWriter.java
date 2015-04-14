package org.joyrest.transform;

import java.io.IOException;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.Route;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonWriter implements Writer {

	private final MediaType supportedMediaType = MediaType.JSON;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void writeTo(InternalResponse<?> response) {
		try {
			mapper.writeValue(response.getOutputStream(), response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from JSON.");
		}
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}

	@Override
	public boolean isCompatible(Route<?, ?> route) {
		return route.getProduces().contains(supportedMediaType);
	}

	@Override
	public boolean isGeneral() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JsonWriter)) return false;

		JsonWriter that = (JsonWriter) o;

		return !(supportedMediaType != null ?
			!supportedMediaType.equals(that.supportedMediaType) : that.supportedMediaType != null);
	}

	@Override
	public int hashCode() {
		return supportedMediaType != null ? supportedMediaType.hashCode() : 0;
	}
}
