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
package org.joyrest.ittest.route;

import java.util.Date;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class PutRouteController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/route");

        put((req, resp) -> {
            resp.status(HttpStatus.OK);
        });

        put("/withBody", (req, resp) -> {
            if (req.getEntity() == null) {
                throw new RuntimeException("No entity added into the route");
            }

            resp.status(HttpStatus.OK);
        }, Req(FeedEntry.class)).consumes(JSON);

        put("/withResponse", (req, resp) -> {
            FeedEntry f = new FeedEntry();
            f.setLink("http://localhost:8080");
            f.setPublishDate(new Date());
            f.setTitle("My Feed Title");
            f.setDescription("My Feed Description");

            resp.entity(f);
            resp.status(HttpStatus.OK);
        }, Resp(FeedEntry.class)).produces(JSON);

        put("/withBoth", (req, resp) -> {
            if (req.getEntity() == null) {
                throw new RuntimeException("No entity added into the route");
            }

            resp.entity(req.getEntity());
            resp.status(HttpStatus.OK);
        }, Req(FeedEntry.class), Resp(FeedEntry.class))
            .produces(JSON).consumes(JSON);
    }
}
