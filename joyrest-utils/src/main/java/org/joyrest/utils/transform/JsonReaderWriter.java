package org.joyrest.utils.transform;

import java.io.IOException;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderWriter extends AbstractReaderWriter {

	private static final ObjectMapper mapper = new ObjectMapper();

	private final MediaType supportedMediaType = MediaType.JSON;

	@Override
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
		try {
			mapper.writeValue(response.getOutputStream(), response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from JSON.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFrom(InternalRequest<Object> request, Type<T> type) {
		try {
			return mapper.readValue(request.getInputStream(), (Class<T>) type.getType());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during unmarshalling from JSON.");
		}
	}

	@Override
	public boolean isClassCompatible(Class<?> clazz) {
		return true;
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
