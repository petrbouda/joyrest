package org.joyrest.utils;

import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.MediaType;
import org.joyrest.transform.Writer;
import org.joyrest.transform.WriterRegistrar;

import java.util.List;
import java.util.Optional;

public final class SerializationUtils {

	public static Writer selectWriter(DefaultMultiMap<MediaType, WriterRegistrar> registrars,
								Class<?> responseClass, MediaType acceptHeader) {
		// Find Writer for dedicated to entity
		List<WriterRegistrar> allNonDefault = registrars.getAllNonDefault(acceptHeader);
		Optional<WriterRegistrar> optRegistrar = allNonDefault.stream().filter(
			registrar -> registrar.getEntityClass().get() == responseClass).findFirst();

		// Find Writer for dedicated to entity
		if(optRegistrar.isPresent()) {
			return optRegistrar.get().getTransformer();
		}

		// Find Default Writer for given media-type
		WriterRegistrar registrar = registrars.getDefault(acceptHeader).orElseThrow(
			() -> new InvalidConfigurationException(
				String.format("No writer for accept header '%s' and class '%s'",
					acceptHeader.getValue(), responseClass)));
		return registrar.getTransformer();
	}

}
