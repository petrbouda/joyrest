package org.joyrest.validation;

import java.util.Set;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class BeanValidationInterceptor implements Interceptor {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public InternalResponse<Object> around(InterceptorChain chain,
                                           InternalRequest<Object> request, InternalResponse<Object> response) {

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request.getEntity());
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        chain.proceed(request, response);
        return response;
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
