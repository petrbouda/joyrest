package org.joyrest.ittest.exception.writer;

import java.util.Objects;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Writer;

public class UnknownWriter implements Writer {

	@Override
	public void writeTo(InternalResponse<?> response) {
		throw new RuntimeException("Should never be thrown");
	}

	@Override
	public boolean isWriterCompatible(InternalRoute route) {
		return false;
	}

	@Override
	public boolean isWriterClassCompatible(Class<?> clazz) {
		return Objects.equals(clazz, Object.class);
	}

	@Override
	public MediaType getMediaType() {
		return MediaType.HTML;
	}

	@Override
	public boolean isGeneral() {
		return false;
	}
}
