package org.joyrest.examples.combiner.routes;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.service.CrudService;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.response.Response;
import org.joyrest.model.request.Request;
import org.joyrest.routing.AbstractControllerConfiguration;
import org.joyrest.routing.TypedControllerConfiguration;

import javax.inject.Inject;
import java.util.List;

public class CombinedFeedRoute extends TypedControllerConfiguration {

    @Inject
    private CrudService<CombinedFeed> feedService;

    @Override
    protected void configure() {
        setGlobalPath("feeds");

        post((Request<CombinedFeed> request, Response<CombinedFeed> response, CombinedFeed insertedFeed) -> {
            CombinedFeed feed = feedService.save(insertedFeed);
            response.entity(feed)
                    .status(HttpStatus.CREATED)
                    .header(HeaderName.LOCATION, getEntityLocation(feed.getId(), request.getPath()));
        }).consumes(MediaType.JSON).produces(MediaType.JSON);

        delete("/{id}", (request, response) -> {
            feedService.delete(request.getPathParam("id").get())
                .orElseThrow(RestException.notFoundSupplier());
            response.status(HttpStatus.NO_CONTENT);
        });

        get("/{id}/entries", (request, response) -> {
            CombinedFeed feed = feedService.get(request.getPathParam("id").get())
                .orElseThrow(RestException.notFoundSupplier());
            response.entity(feed);
        }).produces(MediaType.JSON, MediaType.XML);
    }

    private String getEntityLocation(String entityId, String path){
        return path + "/" + entityId;
    }
}
