package org.joyrest.validation;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.model.http.HttpStatus;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;

public class ConstraintViolationExceptionConfiguration extends TypedExceptionConfiguration {

    @Override
    protected void configure() {
        handle(ConstraintViolationException.class, (req, resp, ex) -> {
            List<ValidationError> errors = ex.getConstraintViolations().stream()
                .map(ValidationError::new)
                .collect(Collectors.toList());

            resp.status(HttpStatus.BAD_REQUEST);
            resp.entity(errors);
        }, RespList(ValidationError.class));
    }
}