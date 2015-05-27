package org.joyrest.examples.servlet.jokeapp.hk2;

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
	private JokeService service;

	@Override
	protected void configure() {
		setControllerPath("jokes");

		post((req, resp) -> {
			Joke savedJoke = service.save(req.getEntity());
			resp.entity(Collections.singletonList(savedJoke))
				.status(CREATED)
				.header(LOCATION, getEntityLocation(savedJoke.getId(), req.getPath()));
		}, Req(Joke.class), RespList(Joke.class))
			.consumes(JSON).produces(JSON);

		get((req, resp) -> {
			List<Joke> jokes = service.getAll();
			resp.entity(jokes);
		}, RespList(Joke.class)).produces(JSON, XML);

		get("{id}", (req, resp) -> {
			Joke joke = service.get(req.getPathParam("id"));
			resp.entity(joke);
		}, Resp(Joke.class)).produces(JSON, XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
