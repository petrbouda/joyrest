package org.joyrest.examples.combiner.routes;

import javax.inject.Inject;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.service.CrudService;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

public class CombinedFeedRoute extends TypedControllerConfiguration {

	@Inject
	private CrudService<CombinedFeed> feedService;

	@Override
	protected void configure() {
		setGlobalPath("feeds");

		post((request, response) -> {
			CombinedFeed feed = feedService.save(request.getEntity());
			response.entity(feed)
				.status(HttpStatus.CREATED)
				.header(HeaderName.LOCATION, getEntityLocation(feed.getId(), request.getPath()));
		}, CombinedFeed.class, CombinedFeed.class).consumes(MediaType.JSON).produces(MediaType.JSON);

		delete("/{id}", (request, response) -> {
			feedService.delete(request.getPathParam("id"))
				.orElseThrow(RestException.notFoundSupplier());
			response.status(HttpStatus.NO_CONTENT);
		});

		get("/{id}/entries", (request, response) -> {
			CombinedFeed feed = feedService.get(request.getPathParam("id"))
				.orElseThrow(RestException.notFoundSupplier());
			response.entity(feed);
		}).produces(MediaType.JSON, MediaType.XML);
	}

	private String getEntityLocation(String entityId, String path) {
		return path + "/" + entityId;
	}
}
