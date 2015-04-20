package org.joyrest.exception.handler;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class RestExceptionHandler implements ExceptionHandler<RestException> {

	@Override
	public void accept(InternalRequest<?> request, InternalResponse<?> response, RestException ex) {
		InternalResponse<?> exResponse = ((RestException) ex).getResponse();
		response.status(exResponse.getStatus());
		exResponse.getHeaders().forEach(response::header);
		exResponse.setOutputStream(response.getOutputStream());
	}

}
