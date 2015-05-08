package org.joyrest.transform;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

public interface Writer extends Transformer {

	void writeTo(InternalResponse<?> response, InternalRequest<?> request);

	boolean isWriterCompatible(InternalRoute route);

}
