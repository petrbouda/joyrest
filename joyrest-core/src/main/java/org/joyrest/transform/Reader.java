package org.joyrest.transform;

import org.joyrest.common.annotation.Default;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.entity.Type;

public interface Reader extends Default, Transformer {

	<T> T readFrom(InternalRequest<T> request, Type<T> clazz);

}
