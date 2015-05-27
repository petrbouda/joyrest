package org.joyrest.ittest.contentType;

import static java.util.Objects.requireNonNull;
import static org.joyrest.model.http.HttpStatus.CREATED;
import static org.joyrest.model.http.HttpStatus.NO_CONTENT;
import static org.joyrest.model.http.MediaType.*;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

public class ContentTypeController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setControllerPath("/ittest/content-type");

		post("/wildcard", (req, resp) -> {
			resp.status(NO_CONTENT);
		}).consumes(MediaType.WILDCARD);

		post("/app-json", (req, resp) -> {
			resp.status(NO_CONTENT);
		}, Req(FeedEntry.class)).consumes(JSON);

		post("/app-json-xml-text", (req, resp) -> {
			requireNonNull(req.getEntity(), "No entity added into the route");
			resp.status(NO_CONTENT);
		}, Req(FeedEntry.class)).consumes(JSON, XML, PLAIN_TEXT);

		post("/app-json-text", (req, resp) -> {
			resp.status(NO_CONTENT);
		}, Req(String.class)).consumes(JSON, PLAIN_TEXT);

		post("/app-json-text-accept-matched", (req, resp) -> {
			resp.status(CREATED);
			resp.entity("Well Done!!");
		}, Req(FeedEntry.class), Resp(String.class))
			.produces(XML, JSON, PLAIN_TEXT).consumes(XML, JSON, PLAIN_TEXT);
	}
}
