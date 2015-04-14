package org.joyrest.transform;

import org.joyrest.common.annotation.General;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.entity.Type;

public interface Reader extends General, Transformer {

	<T> T readFrom(InternalRequest<T> request, Type<T> clazz);

}
