package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.AbstractControllerConfiguration;

import static org.joyrest.model.http.HttpStatus.*;

public class StatusController extends AbstractControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/status");

		get("/200", (req, resp) -> resp.status(OK));

		post("/201", (Request req, Response resp, FeedEntry body) -> {
			resp.status(HttpStatus.CREATED);
		}).consumes(MediaType.JSON);

		post("/204", (req, resp) -> resp.status(NO_CONTENT));

		get("/301", (req, resp) -> resp.status(MOVED_PERMANENTLY));

		get("/302", (req, resp) -> resp.status(FOUND));

		post("/400", (Request req, Response resp, FeedEntry body) -> {
			resp.status(BAD_REQUEST);
		}).consumes(MediaType.JSON);

		get("/403", (req, resp) -> resp.status(FORBIDDEN));

		get("/404", (req, resp) -> resp.status(NOT_FOUND));

		get("/500", (req, resp) -> resp.status(INTERNAL_SERVER_ERROR));

		get("/501", (req, resp) -> resp.status(NOT_IMPLEMENTED));
	}
}
