package org.joyrest.examples.servlet;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.TypedControllerConfiguration;
import org.joyrest.routing.entity.GenericType;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.joyrest.routing.entity.GenericType.List;

public class JokeController extends TypedControllerConfiguration {

	@Inject
	private JokeService service;

	@Override
	protected void configure() {
		setGlobalPath("jokes");

		post((request, response) -> {
			Joke savedJoke = service.save(null);
			response.entity(null)
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
		}, List(Joke.class), Joke.class).consumes(MediaType.JSON).produces(MediaType.JSON);


		post((request, response) -> {
			Joke savedJoke = service.save(null);
			response.entity(null)
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
		}, List(Joke.class), List(Joke.class)).consumes(MediaType.JSON).produces(MediaType.JSON);
//
//		get((request, response) -> {
//			List<Joke> jokes = service.getAll();
//			response.entity(jokes);
//		}).produces(MediaType.JSON, MediaType.XML);
//
//		get(":id", (request, response) -> {
//			Joke joke = service.get(request.getPathParam("id"));
//			response.entity(joke);
//		}).produces(MediaType.JSON, MediaType.XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
