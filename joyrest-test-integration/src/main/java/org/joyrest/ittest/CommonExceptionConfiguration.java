package org.joyrest.ittest;

import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.ittest.entity.Error;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class CommonExceptionConfiguration extends TypedExceptionConfiguration {

    @Override
    protected void configure() {

        handle(NumberFormatException.class, (req, resp, ex) -> {
            Error error = new Error();
            error.setReason("NumberFormatException");
            error.setDescription(ex.getMessage());

            resp.status(HttpStatus.BAD_REQUEST);
            resp.header(HeaderName.of("x-special-header"), "SPECIAL");
            resp.entity(error);
        }, Resp(Error.class));
    }
}
