package org.joyrest.ittest.exception;

import static org.joyrest.model.http.MediaType.*;

import org.joyrest.exception.type.RestException;
import org.joyrest.ittest.exception.exceptions.ContactException;
import org.joyrest.ittest.exception.exceptions.FourthException;
import org.joyrest.ittest.exception.exceptions.SecondException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class ExceptionController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setControllerPath("/ittest/exception");

		get("badRequest", (req, resp) -> {
			throw new RestException(HttpStatus.BAD_REQUEST, "Bad Request !!!");
		});

		get("numberFormat", (req, resp) -> {
			throw new NumberFormatException("Bad number format exception");
		}).produces(JSON);

		get("unknownWriter", (req, resp) -> {
			throw new NumberFormatException("Bad number format exception");
		}).produces(HTML);

		post("parent", (req, resp) -> {
			throw new SecondException();
		}).produces(JSON);

		post("parentWithBody", (req, resp) -> {
			throw new FourthException();
		}).produces(JSON);

		post("onlySpecialWriter", (req, resp) -> {
			throw new ContactException();
		}).produces(XML);

	}

}
