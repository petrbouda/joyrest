package org.joyrest.ittest.path;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class PathRouteController2 extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setControllerPath("/ittest/path2");

		post("/", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

	}
}
