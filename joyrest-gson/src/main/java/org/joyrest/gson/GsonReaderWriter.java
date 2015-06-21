package org.joyrest.gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

import com.google.gson.Gson;

public class GsonReaderWriter extends AbstractReaderWriter {

	private static final Gson GSON = new Gson();

	private static final MediaType SUPPORTED_MEDIA_TYPE = MediaType.JSON;

	@Override
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
		try (PrintStream ps = new PrintStream(response.getOutputStream())) {
			GSON.toJson(response.getEntity().get(), ps);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFrom(InternalRequest<Object> request, Type<T> type) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		return GSON.fromJson(bufferedReader, (Class<T>) type.getType());
	}

	@Override
	public boolean isClassCompatible(Class<?> clazz) {
		return true;
	}

	@Override
	public MediaType getMediaType() {
		return SUPPORTED_MEDIA_TYPE;
	}
}
