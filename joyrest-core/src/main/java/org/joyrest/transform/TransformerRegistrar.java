package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.http.MediaType;

import java.util.Optional;

public interface TransformerRegistrar<T extends Transformer> extends Default {

	T getTransformer();

	MediaType getMediaType();

	Optional<Class<?>> getEntityClass();

}
