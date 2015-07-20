/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.examples.combiner.routes;

import javax.inject.Inject;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.model.FeedEntry;
import org.joyrest.examples.combiner.service.CrudService;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.exception.type.RestException.notFoundSupplier;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseCollectionType.RespList;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class CombinedFeedController extends TypedControllerConfiguration {

    @Inject
    private CrudService<CombinedFeed> feedService;

    @Override
    protected void configure() {
        setControllerPath("feeds");

        post((request, response) -> {
            CombinedFeed feed = feedService.save(request.getEntity());
            response.entity(feed)
                .status(HttpStatus.CREATED)
                .header(HeaderName.LOCATION, getEntityLocation(feed.getId(), request.getPath()));
        }, Req(CombinedFeed.class), Resp(CombinedFeed.class))
            .consumes(MediaType.JSON).produces(MediaType.JSON);

        delete("/{id}", (request, response) -> {
            feedService.delete(request.getPathParam("id"))
                .orElseThrow(notFoundSupplier("No Feed was found for deletion."));
            response.status(HttpStatus.NO_CONTENT);
        });

        get("/{id}/combined", (request, response) -> {
            CombinedFeed feed = feedService.get(request.getPathParam("id"))
                .orElseThrow(notFoundSupplier("No Feed was found."));
            response.entity(feed);
        }, Resp(CombinedFeed.class)).produces(MediaType.JSON, MediaType.XML);

        get("/{id}/entries", (request, response) -> {
            CombinedFeed feed = feedService.get(request.getPathParam("id"))
                .orElseThrow(notFoundSupplier("No Feed was found."));
            response.entity(feed.getFeedEntries());
        }, RespList(FeedEntry.class)).produces(MediaType.JSON, MediaType.XML);
    }

    private String getEntityLocation(String entityId, String path) {
        return path + "/" + entityId;
    }
}
