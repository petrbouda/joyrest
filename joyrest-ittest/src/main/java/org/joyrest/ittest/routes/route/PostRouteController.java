package org.joyrest.ittest.routes.route;

import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class PostRouteController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/route");

		post((req, resp) -> resp.status(HttpStatus.OK)).consumes(JSON);

		post("/withPathWithConsume", (req, resp) -> resp.status(HttpStatus.OK)).consumes(JSON);

		post("/withPath", (req, resp) -> resp.status(HttpStatus.OK));

		post("/withBody", (req, resp) -> {
			if (req.getEntity() == null)
				throw new RuntimeException("No entity added into the route");

			resp.status(HttpStatus.OK);
		}, Req(FeedEntry.class)).consumes(JSON);

		post("/withBodyAndResponse", (req, resp) -> {
			if (req.getEntity() == null)
				throw new RuntimeException("No entity added into the route");

			resp.entity(req.getEntity());
			resp.status(HttpStatus.CREATED);
		}, FeedEntry.class, FeedEntry.class)
			.consumes(JSON).produces(JSON);
	}
}
