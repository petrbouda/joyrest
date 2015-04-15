package org.joyrest.ittest.routes.route;

import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.ResponseType.Resp;

import java.util.Date;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class GetRouteController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/route");

		get((req, resp) -> {
			resp.status(HttpStatus.OK);
		});

		get("/withPath", (req, resp) -> {
			resp.status(HttpStatus.OK);
		});

		get("/withResponse", (req, resp) -> {
			FeedEntry f = new FeedEntry();
			f.setLink("http://localhost:8080");
			f.setPublishDate(new Date());
			f.setTitle("My Feed Title");
			f.setDescription("My Feed Description");

			resp.entity(f);
			resp.status(HttpStatus.CREATED);
		}, Resp(FeedEntry.class)).produces(JSON);
	}
}
