package org.joyrest.ittest.route;

import static java.util.Objects.requireNonNull;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import org.joyrest.ittest.entity.FeedEntry;
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
			requireNonNull(req.getEntity(), "No entity added into the route");

			resp.entity(req.getEntity());
			resp.status(HttpStatus.CREATED);
		}, Req(FeedEntry.class), Resp(FeedEntry.class))
			.consumes(JSON).produces(JSON);
	}
}
