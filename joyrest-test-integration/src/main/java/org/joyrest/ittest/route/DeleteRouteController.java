package org.joyrest.ittest.route;

import static org.joyrest.routing.entity.RequestType.Req;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

public class DeleteRouteController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setControllerPath("/ittest/route");

		delete((req, resp) -> {
			resp.status(HttpStatus.OK);
		});

		delete("/withoutBody", (req, resp) -> {
			if (req.getEntity() != null)
				throw new RuntimeException("No entity added into the route");

			resp.status(HttpStatus.OK);
		});

		delete("/withBody", (req, resp) -> {
			if (!req.getEntity().equals("id-feed-entry"))
				throw new RuntimeException("There is no correct plain/text body");

			resp.status(HttpStatus.OK);
		}, Req(String.class)).consumes(MediaType.PLAIN_TEXT);

	}
}
