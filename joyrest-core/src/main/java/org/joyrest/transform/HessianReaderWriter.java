package org.joyrest.transform;

import com.caucho.hessian.io.*;
import com.sun.xml.internal.ws.developer.Serialization;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

import java.io.IOException;

public class HessianReaderWriter extends AbstractReaderWriter {

    private final MediaType supportedMediaType = MediaType.SERIALIZATION_HESSIAN;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFrom(InternalRequest<T> request, Type<T> clazz) {
        try {
            Hessian2Input in = new Hessian2Input(request.getInputStream());
            in.startMessage();
            T object = (T) in.readObject();
            in.completeMessage();
            return object;
        } catch (IOException e) {
            throw new RuntimeException("An error occurred during unmarshalling from Hessian.", e);
        }
    }

    @Override
    public void writeTo(InternalResponse<?> response) {
        try {
            Hessian2Output out = new Hessian2Output(response.getOutputStream());
            out.startMessage();
            out.writeObject(response.getEntity().get());
            out.completeMessage();
            out.flushBuffer();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred during marshalling to Hessian.", e);
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
