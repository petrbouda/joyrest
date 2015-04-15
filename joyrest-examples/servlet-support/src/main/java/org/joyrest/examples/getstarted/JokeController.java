package org.joyrest.examples.getstarted;

import javax.inject.Inject;

import org.joyrest.routing.TypedControllerConfiguration;

public class JokeController extends TypedControllerConfiguration {

	@Inject
	private JokeService service;

	@Override
	protected void configure() {
		setGlobalPath("jokes");

		// post((Request<Joke> request, Response<List<Joke>> response) -> {
		// Joke savedJoke = service.save(request.getEntity().get());
		// response.entity(Arrays.asList(savedJoke))
		// .status(HttpStatus.CREATED)
		// .header(HeaderName.LOCATION, getEntityLocation(savedJoke.getId(), request.getPath()));
		// }).consumes(MediaType.JSON).produces(MediaType.JSON);
		//
		// get((request, response) -> {
		// List<Joke> jokes = service.getAll();
		// response.entity(jokes);
		// }, Joke.class, List(Joke.class))
		// .produces(MediaType.JSON, MediaType.XML);
		//
		// get(":id", (request, response) -> {
		// Joke joke = service.get(request.getPathParam("id").get());
		// response.entity(joke);
		// }).produces(MediaType.JSON, MediaType.XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
