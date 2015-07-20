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
package org.joyrest.ittest.status;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.HttpStatus.BAD_REQUEST;
import static org.joyrest.model.http.HttpStatus.FORBIDDEN;
import static org.joyrest.model.http.HttpStatus.FOUND;
import static org.joyrest.model.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.joyrest.model.http.HttpStatus.MOVED_PERMANENTLY;
import static org.joyrest.model.http.HttpStatus.NOT_FOUND;
import static org.joyrest.model.http.HttpStatus.NOT_IMPLEMENTED;
import static org.joyrest.model.http.HttpStatus.NO_CONTENT;
import static org.joyrest.model.http.HttpStatus.OK;

public class StatusController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/status");

        get("/200", (req, resp) -> resp.status(OK));

        post("/201", (req, resp) -> resp.status(HttpStatus.CREATED))
            .consumes(MediaType.JSON);

        post("/204", (req, resp) -> resp.status(NO_CONTENT));

        get("/301", (req, resp) -> resp.status(MOVED_PERMANENTLY));

        get("/302", (req, resp) -> resp.status(FOUND));

        post("/400", (req, resp) -> resp.status(BAD_REQUEST))
            .consumes(MediaType.JSON);

        get("/403", (req, resp) -> resp.status(FORBIDDEN));

        get("/404", (req, resp) -> resp.status(NOT_FOUND));

        get("/500", (req, resp) -> resp.status(INTERNAL_SERVER_ERROR));

        get("/501", (req, resp) -> resp.status(NOT_IMPLEMENTED));
    }
}
