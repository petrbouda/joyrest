package org.joyrest.utils.transform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

public class JavaSerializationReaderWriter extends AbstractReaderWriter {

	private final MediaType supportedMediaType = MediaType.SERIALIZATION_JAVA;

	@Override
	@SuppressWarnings("unchecked")
	public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
		try {
			ObjectInputStream in = new ObjectInputStream(request.getInputStream());
			return (T) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("An error occurred during unmarshalling from Java Serialization.", e);
		}
	}

	@Override
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from Java Serialization.", e);
		}
	}

	@Override
	public boolean isClassCompatible(Class<?> clazz) {
		return Serializable.class.isAssignableFrom(clazz);
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
