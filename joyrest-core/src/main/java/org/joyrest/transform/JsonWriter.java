package org.joyrest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;

import java.io.IOException;
import java.util.List;

public class JsonWriter implements Writer {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void writeTo(Response response) {
		try {
			mapper.writeValue(response.getOutputStream(), response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from JSON.");
		}
	}

	@Override
	public MediaType[] getMediaTypes() {
		return new MediaType[] { MediaType.JSON};
	}

	@Override
	public boolean test(Route route) {
		List<MediaType> produces = route.getProduces();
		return produces.contains(getMediaTypes());
	}

	@Override
	public boolean isDefault() {
		return true;
	}
}
