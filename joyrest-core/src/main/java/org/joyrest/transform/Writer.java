package org.joyrest.transform;

import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

import java.util.Optional;

public interface Writer extends Transformer {

	void writeTo(InternalResponse<?> response);

	boolean isWriterCompatible(InternalRoute route);

	Optional<Class<?>> getWriterCompatibleClass();

}
