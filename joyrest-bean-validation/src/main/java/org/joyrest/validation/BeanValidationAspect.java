package org.joyrest.validation;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

import javax.validation.*;
import java.util.Set;

public class BeanValidationAspect implements Aspect {

	private static final Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public InternalResponse<Object> around(AspectChain chain,
		InternalRequest<Object> request, InternalResponse<Object> response) {

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request.getEntity());
		if(!constraintViolations.isEmpty())
			throw new ConstraintViolationException(constraintViolations);

		chain.proceed(request, response);
		return response;
	}

	@Override
	public int getOrder() {
		return 20;
	}
}
