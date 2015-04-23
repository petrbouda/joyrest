package org.joyrest.transform;

import java.io.IOException;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderWriter extends AbstractReaderWriter {

	private static final ObjectMapper mapper = new ObjectMapper();
	private final MediaType supportedMediaType = MediaType.JSON;

	@Override
	public void writeTo(InternalResponse<?> response) {
		try {
			mapper.writeValue(response.getOutputStream(), response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from JSON.");
		}
	}

	@Override
	public <T> T readFrom(InternalRequest<T> request, Type<T> type) {
		try {
			return mapper.readValue(request.getInputStream(), (Class<T>) type.getType());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during unmarshalling from JSON.");
		}
	}

	@Override
	public boolean isWriterClassCompatible(Class<?> clazz) {
		return true;
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
