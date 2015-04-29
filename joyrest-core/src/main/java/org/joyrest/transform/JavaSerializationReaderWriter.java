package org.joyrest.transform;

import com.sun.xml.internal.ws.developer.Serialization;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

import java.io.*;

public class JavaSerializationReaderWriter extends AbstractReaderWriter {

    private final MediaType supportedMediaType = MediaType.SERIALIZATION_JAVA;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFrom(InternalRequest<T> request, Type<T> clazz) {
        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("An error occurred during unmarshalling from Java Serialization.", e);
        }
    }

    @Override
    public void writeTo(InternalResponse<?> response) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
            out.writeObject(response.getEntity().get());
        } catch (IOException e) {
            throw new RuntimeException("An error occurred during marshalling from Java Serialization.", e);
        }
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return Serialization.class.isAssignableFrom(clazz);
    }

    @Override
    public MediaType getMediaType() {
        return supportedMediaType;
    }
}
