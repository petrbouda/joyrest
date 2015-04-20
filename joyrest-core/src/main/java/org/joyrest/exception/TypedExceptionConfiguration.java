package org.joyrest.exception;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.RequestType;
import org.joyrest.routing.entity.ResponseType;

public abstract class TypedExceptionConfiguration extends AbstractExceptionConfiguration {

	protected final <T extends Exception> void exception(Class<T> clazz,
			TriConsumer<InternalRequest<?>, InternalResponse<?>, T> handler) {
		putHandler(clazz, handler);
	}

	protected final <T extends Exception, REQ, RESP> void exception(Class<T> clazz,
			TriConsumer<InternalRequest<REQ>, InternalResponse<RESP>, T> handler, RequestType<REQ> req, ResponseType<RESP> resp) {
		putHandler(clazz, handler);
	}

	// TODO Rest of the exceptions

}
