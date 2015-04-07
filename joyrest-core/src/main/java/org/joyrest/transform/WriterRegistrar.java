package org.joyrest.transform;

import org.joyrest.model.http.MediaType;

public class WriterRegistrar extends AbstractTransformerRegistrar<Writer> {

	public WriterRegistrar(Writer transformer, MediaType mediaType) {
		super(transformer, mediaType);
	}

	public WriterRegistrar(Writer transformer, MediaType mediaType, Class<?> entity) {
		super(transformer, mediaType, entity);
	}
}
