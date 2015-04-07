package org.joyrest.transform;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public abstract class AbstractTransformerRegistrar<T extends Transformer> implements TransformerRegistrar<T> {

	private final T transformer;

	private final MediaType mediaType;

	private final Class<?> entityClass;

	public AbstractTransformerRegistrar(T transformer, MediaType mediaType) {
		this(transformer, mediaType, null);
	}

	public AbstractTransformerRegistrar(T transformer, MediaType mediaType, Class<?> entityClass) {
		requireNonNull(transformer);
		requireNonNull(mediaType);

		if (!contains(transformer.getMediaTypes(), mediaType))
			throw new InvalidConfigurationException(
				String.format("Transformer '%s' is not suitable for this media type '%s'",
					transformer.getClass().getSimpleName(), mediaType));

		this.transformer = transformer;
		this.mediaType = mediaType;
		this.entityClass = entityClass;
	}

	@Override
	public T getTransformer() {
		return transformer;
	}

	@Override
	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public Optional<Class<?>> getEntityClass() {
		return Optional.ofNullable(entityClass);
	}

	@Override
	public boolean isDefault() {
		return transformer.isDefault();
	}

	private static boolean contains(MediaType[] mediaTypes, MediaType mediaType) {
		return Arrays.asList(mediaTypes).contains(mediaType);
	}
}
