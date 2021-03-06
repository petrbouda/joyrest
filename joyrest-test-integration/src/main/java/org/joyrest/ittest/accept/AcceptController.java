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
package org.joyrest.ittest.accept;

import java.util.Date;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.HttpStatus.NO_CONTENT;
import static org.joyrest.model.http.HttpStatus.OK;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.PLAIN_TEXT;
import static org.joyrest.model.http.MediaType.XML;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import static java.util.Objects.requireNonNull;

public class AcceptController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        FeedEntry f = new FeedEntry();
        f.setLink("http://localhost:8080");
        f.setPublishDate(new Date());
        f.setTitle("My Feed Title");
        f.setDescription("My Feed Description");

        setControllerPath("/ittest/accept");

        post("/wildcard", (req, resp) -> {
            resp.status(NO_CONTENT);
        }).produces(MediaType.WILDCARD);

        post("/app-json", (req, resp) -> {
            resp.status(OK);
            resp.entity(f);
        }, Resp(FeedEntry.class))
            .produces(JSON);

        post("/app-json-xml", (req, resp) -> {
            requireNonNull(req.getEntity(), "No entity added into the route");
            resp.status(CREATED);
            resp.entity(f);
        }, Req(FeedEntry.class), Resp(FeedEntry.class))
            .produces(JSON, XML).consumes(JSON, XML, PLAIN_TEXT);

        get((req, resp) -> {
            resp.entity(f);
            resp.status(OK);
        }, Resp(FeedEntry.class))
            .produces(MediaType.JSON);
    }
}
