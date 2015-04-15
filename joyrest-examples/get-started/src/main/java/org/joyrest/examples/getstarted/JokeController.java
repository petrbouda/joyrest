package org.joyrest.examples.getstarted;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.TypedControllerConfiguration;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.joyrest.routing.entity.ResponseCollectionType.RespList;

public class JokeController extends TypedControllerConfiguration {

	@Inject
	private JokeService service;

	@Override
	protected void configure() {
		setGlobalPath("jokes");

		post((request, response) -> {
			Joke savedJoke = service.save(request.getEntity());
			response.entity(Arrays.asList(savedJoke))
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
		}, Joke.class, RespList(Joke.class)).consumes(MediaType.JSON).produces(MediaType.JSON);

//		post((request, response) -> {
//			Joke savedJoke = service.save(null);
//			response.entity(null)
//				.status(HttpStatus.CREATED)
//				.header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
//		}, List(Joke.class), List(Joke.class)).consumes(MediaType.JSON).produces(MediaType.JSON);
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
