package org.joyrest.examples.combiner.exception;

import org.joyrest.exception.AbstractExceptionConfiguration;
import org.joyrest.model.http.HttpStatus;

import javax.validation.ConstraintViolationException;

public class ExceptionHandlerConfiguration extends AbstractExceptionConfiguration {

	@Override
	protected void configure() {
		// TODO
//		exception(ConstraintViolationException.class, (request, response, ex) -> {
//			Error error = new Error();
//			ex.getConstraintViolations().stream()
//				.forEach(violation -> error.addMessage(violation.getMessage()));
//
//			response.entity(error)
//				.status(HttpStatus.BAD_REQUEST);
//		});
	}
}
