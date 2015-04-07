package org.joyrest.examples.servlet;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.AbstractControllerConfiguration;

import javax.inject.Inject;
import java.util.List;

public class JokeController extends AbstractControllerConfiguration {

	@Inject
	private JokeService service;

	@Override
	protected void configure() {
		setGlobalPath("jokes");

		post((Request request, Response response, Joke joke) -> {
			Joke savedJoke = service.save(joke);
			response.entity(savedJoke)
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
		}).consumes(MediaType.JSON).produces(MediaType.JSON);

		get((request, response) -> {
			List<Joke> jokes = service.getAll();
			response.entity(jokes);
		}).produces(MediaType.JSON, MediaType.XML);

		get(":id", (request, response) -> {
			Joke joke = service.get(request.getPathParam("id").get());
			response.entity(joke);
		}).produces(MediaType.JSON, MediaType.XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
