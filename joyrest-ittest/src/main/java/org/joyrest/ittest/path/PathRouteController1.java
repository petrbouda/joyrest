package org.joyrest.ittest.path;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class PathRouteController1 extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/path");

		post((req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

		post("path0", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

		post("path1/", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

		post("/path2/", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

		post("/path3", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

		post("/path1/path2/", (req, resp) -> {
			resp.status(HttpStatus.NO_CONTENT);
		});

	}
}
