package org.joyrest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;

import java.io.IOException;
import java.util.List;

public class JsonReader<T> implements Reader<T> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public T readFrom(InternalRequest<T> request, Class<T> clazz) {
		try {
			return mapper.readValue(request.getRequestBody(), clazz);
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during unmarshalling from JSON.");
		}
	}

	@Override
	public MediaType[] getMediaTypes() {
		return new MediaType[] { MediaType.JSON };
	}

	@Override
	public boolean test(Route<T, ?> route) {
		List<MediaType> consumes = route.getConsumes();
		return consumes.contains(getMediaTypes());
	}

	@Override
	public boolean isDefault() {
		return true;
	}
}
