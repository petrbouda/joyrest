package org.joyrest.perftest.joyrest.feeds.resources;

import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;
import static org.joyrest.routing.entity.ResponseType.Resp;

import java.util.List;

import javax.inject.Inject;

import org.joyrest.perftest.joyrest.feeds.model.FeedEntry;
import org.joyrest.perftest.joyrest.feeds.service.FeedService;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class FeedController extends TypedControllerConfiguration {

	@Inject
	private FeedService feedService;

	@Override
	protected void configure() {
		setGlobalPath("feeds");

		post((req, resp) -> {
			FeedEntry entry = feedService.save(req.getEntity());
			resp.entity(entry);
			resp.status(HttpStatus.OK);
		}, Req(FeedEntry.class), Resp(FeedEntry.class))
			.consumes(JSON).produces(JSON);

		get((req, resp) -> {
			List<FeedEntry> entries = feedService.getAll();
			resp.entity(entries);
			resp.status(HttpStatus.OK);
		}, RespList(FeedEntry.class))
			.produces(JSON);

		get("ping", (req, resp) -> {
			feedService.ping();
			resp.status(HttpStatus.NO_CONTENT);
		});
	}
}
