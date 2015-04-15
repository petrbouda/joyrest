package org.joyrest.transform;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.entity.Type;

public interface Reader extends Transformer {

	<T> T readFrom(InternalRequest<T> request, Type<T> clazz);

	boolean isReaderCompatible(EntityRoute route);

}
