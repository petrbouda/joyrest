package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.AbstractControllerConfiguration;

public class ContentTypeController extends AbstractControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/content-type/");

		post("/withBodyJson", (req, resp, body) -> {
			resp.status(HttpStatus.OK);
		}, FeedEntry.class).consumes(MediaType.JSON);

		post("/withBodyXml", (req, resp, body) -> {
			resp.status(HttpStatus.OK);
		}, FeedEntry.class).consumes(MediaType.XML);

		post("/withoutBody", (req, resp) -> {
			resp.status(HttpStatus.OK);
		}).consumes(MediaType.JSON);

		post("/missingWithBody", (req, resp, body) -> {
			resp.status(HttpStatus.OK);
		}, FeedEntry.class);

		post("/missingWithoutBody", (req, resp) -> {
			resp.status(HttpStatus.OK);
		});

		post("/wildcardWithBody", (req, resp, body) -> {
			resp.status(HttpStatus.OK);
		}, FeedEntry.class).consumes(MediaType.WILDCARD);

		post("/wildcardWithoutBody", (req, resp) -> {
			resp.status(HttpStatus.OK);
		}).consumes(MediaType.WILDCARD);
	}
}
