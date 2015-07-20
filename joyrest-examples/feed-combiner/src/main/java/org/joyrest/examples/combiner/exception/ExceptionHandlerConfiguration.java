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
package org.joyrest.examples.combiner.exception;

import org.joyrest.exception.configuration.AbstractExceptionConfiguration;

public class ExceptionHandlerConfiguration extends AbstractExceptionConfiguration {

    @Override
    protected void configure() {
        // TODO
        // exception(ConstraintViolationException.class, (model, response, ex) -> {
        // Error error = new Error();
        // ex.getConstraintViolations().stream()
        // .forEach(violation -> error.addMessage(violation.getMessage()));
        //
        // response.entity(error)
        // .status(HttpStatus.BAD_REQUEST);
        // });
    }
}
