package org.joyrest.transform;

import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;

public interface Writer extends Transformer {

	void writeTo(InternalResponse<?> response);

	boolean isWriterCompatible(EntityRoute route);
}
