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

import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.routing.entity.RequestType.Req;

public class DeleteRouteController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/route");

        delete((req, resp) -> {
            resp.status(HttpStatus.OK);
        });

        delete("/withoutBody", (req, resp) -> {
            if (req.getEntity() != null) {
                throw new RuntimeException("No entity added into the route");
            }

            resp.status(HttpStatus.OK);
        });

        delete("/withBody", (req, resp) -> {
            if (!req.getEntity().equals("id-feed-entry")) {
                throw new RuntimeException("There is no correct plain/text body");
            }

            resp.status(HttpStatus.OK);
        }, Req(String.class)).consumes(MediaType.PLAIN_TEXT);

    }
}
