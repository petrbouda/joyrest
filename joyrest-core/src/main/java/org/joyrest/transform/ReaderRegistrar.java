package org.joyrest.transform;

import org.joyrest.model.http.MediaType;

public class ReaderRegistrar extends AbstractTransformerRegistrar<Reader> {

	public ReaderRegistrar(Reader transformer, MediaType mediaType) {
		super(transformer, mediaType);
	}

	public ReaderRegistrar(Reader transformer, MediaType mediaType, Class<?> entity) {
		super(transformer, mediaType, entity);
	}

}
