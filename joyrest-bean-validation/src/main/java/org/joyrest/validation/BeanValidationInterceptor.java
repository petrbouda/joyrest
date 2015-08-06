package org.joyrest.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.interceptor.InterceptorInternalOrders;

public class BeanValidationInterceptor implements Interceptor {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp) {

        Set<ConstraintViolation<Object>> requestConstraintViolations = validator.validate(req.getEntity());
        if (!requestConstraintViolations.isEmpty()) {
            throw new ConstraintViolationException(requestConstraintViolations);
        }

        chain.proceed(req, resp);

        Set<ConstraintViolation<Object>> responseConstraintViolations = validator.validate(req.getEntity());
        if (!responseConstraintViolations.isEmpty()) {
            throw new ConstraintViolationException(responseConstraintViolations);
        }

        return resp;
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.VALIDATION;
    }
}
