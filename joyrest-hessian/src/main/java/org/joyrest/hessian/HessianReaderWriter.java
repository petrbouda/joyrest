package org.joyrest.hessian;

import java.io.IOException;
import java.io.Serializable;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

public class HessianReaderWriter extends AbstractReaderWriter {

	private final MediaType supportedMediaType = MediaType.SERIALIZATION_HESSIAN;

	@Override
	@SuppressWarnings("unchecked")
	public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
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
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
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
		return Serializable.class.isAssignableFrom(clazz);
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
