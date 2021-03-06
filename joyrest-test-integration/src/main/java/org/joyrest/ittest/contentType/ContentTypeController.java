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
package org.joyrest.ittest.contentType;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.HttpStatus.NO_CONTENT;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.PLAIN_TEXT;
import static org.joyrest.model.http.MediaType.XML;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import static java.util.Objects.requireNonNull;

public class ContentTypeController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/content-type");

        post("/wildcard", (req, resp) -> {
            resp.status(NO_CONTENT);
        }).consumes(MediaType.WILDCARD);

        post("/app-json", (req, resp) -> {
            resp.status(NO_CONTENT);
        }, Req(FeedEntry.class)).consumes(JSON);

        post("/app-json-xml-text", (req, resp) -> {
            requireNonNull(req.getEntity(), "No entity added into the route");
            resp.status(NO_CONTENT);
        }, Req(FeedEntry.class)).consumes(JSON, XML, PLAIN_TEXT);

        post("/app-json-text", (req, resp) -> {
            resp.status(NO_CONTENT);
        }, Req(String.class)).consumes(JSON, PLAIN_TEXT);

        post("/app-json-text-accept-matched", (req, resp) -> {
            resp.status(CREATED);
            resp.entity("Well Done!!");
        }, Req(FeedEntry.class), Resp(String.class))
            .produces(XML, JSON, PLAIN_TEXT).consumes(XML, JSON, PLAIN_TEXT);
    }
}
