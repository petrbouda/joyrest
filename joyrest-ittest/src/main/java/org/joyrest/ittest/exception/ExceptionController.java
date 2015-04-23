package org.joyrest.ittest.exception;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

import static org.joyrest.model.http.MediaType.JSON;

public class ExceptionController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/exception");

		get("badRequest", (req, resp) -> {
			throw new RestException(HttpStatus.BAD_REQUEST, "Bad Request !!!");
		});

		get("numberFormat", (req, resp) -> {
			throw new NumberFormatException("Bad number format exception");
		}).produces(JSON);

	}

}
