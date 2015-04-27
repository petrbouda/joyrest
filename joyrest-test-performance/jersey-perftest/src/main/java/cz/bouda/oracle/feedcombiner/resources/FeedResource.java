package cz.bouda.oracle.feedcombiner.resources;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.bouda.oracle.feedcombiner.model.FeedEntry;
import cz.bouda.oracle.feedcombiner.service.FeedService;
import cz.bouda.oracle.feedcombiner.service.FeedServiceImpl;

import java.util.List;

@Path("feeds")
public class FeedResource {

	@Inject
	private FeedService feedService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(FeedEntry insertedFeed) {
		FeedEntry feed = feedService.save(insertedFeed);
		return Response.ok(feed).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<FeedEntry> feedEntries = feedService.getAll();
		return Response.ok(new GenericEntity<List<FeedEntry>>(feedEntries) {}).build();
	}

	@GET
	@Path("ping")
	public Response ping() {
		feedService.ping();
		return Response.noContent().build();
	}

}
