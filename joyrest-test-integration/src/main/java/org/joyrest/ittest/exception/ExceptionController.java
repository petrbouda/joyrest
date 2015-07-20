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
package org.joyrest.ittest.exception;

import org.joyrest.exception.type.RestException;
import org.joyrest.ittest.exception.exceptions.ContactException;
import org.joyrest.ittest.exception.exceptions.FourthException;
import org.joyrest.ittest.exception.exceptions.SecondException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.MediaType.HTML;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.XML;

public class ExceptionController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/exception");

        get("badRequest", (req, resp) -> {
            throw new RestException(HttpStatus.BAD_REQUEST, "Bad Request");
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
