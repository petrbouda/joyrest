package org.joyrest.examples.di.jokeapp;

import static org.joyrest.model.http.HeaderName.LOCATION;
import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.XML;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;
import static org.joyrest.routing.entity.ResponseType.Resp;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.joyrest.routing.TypedControllerConfiguration;

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
