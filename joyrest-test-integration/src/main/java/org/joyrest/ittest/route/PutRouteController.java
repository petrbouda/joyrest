package org.joyrest.ittest.route;

import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import java.util.Date;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class PutRouteController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/route");

		put((req, resp) -> {
			resp.status(HttpStatus.OK);
		});

		put("/withBody", (req, resp) -> {
			if (req.getEntity() == null)
				throw new RuntimeException("No entity added into the route");

			resp.status(HttpStatus.OK);
		}, Req(FeedEntry.class)).consumes(JSON);

		put("/withResponse", (req, resp) -> {
			FeedEntry f = new FeedEntry();
			f.setLink("http://localhost:8080");
			f.setPublishDate(new Date());
			f.setTitle("My Feed Title");
			f.setDescription("My Feed Description");

			resp.entity(f);
			resp.status(HttpStatus.OK);
		}, Resp(FeedEntry.class)).produces(JSON);

		put("/withBoth", (req, resp) -> {
			if (req.getEntity() == null)
				throw new RuntimeException("No entity added into the route");

			resp.entity(req.getEntity());
			resp.status(HttpStatus.OK);
		}, FeedEntry.class, FeedEntry.class).produces(JSON).consumes(JSON);
	}
}
