/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
