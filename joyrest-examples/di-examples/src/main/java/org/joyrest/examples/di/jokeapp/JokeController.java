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
package org.joyrest.examples.di.jokeapp;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.HeaderName.LOCATION;
import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.XML;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class JokeController extends TypedControllerConfiguration {

    @Inject
    JokeService service;

    @Override
    protected void configure() {
        setControllerPath("jokes");

        post((request, response) -> {
            Joke savedJoke = service.save(request.getEntity());
            response.entity(Collections.singletonList(savedJoke))
                .status(CREATED)
                .header(LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
        }, Req(Joke.class), RespList(Joke.class))
            .consumes(JSON).produces(JSON);

        get((request, response) -> {
            List<Joke> jokes = service.getAll();
            response.entity(jokes);
        }, RespList(Joke.class)).produces(JSON, XML);

        get("{id}", (request, response) -> {
            Joke joke = service.get(request.getPathParam("id"));
            response.entity(joke);
        }, Resp(Joke.class)).produces(JSON, XML);
    }

    private String getEntityLocation(String entityId, String path) {
        return path + "/" + entityId;
    }
}
