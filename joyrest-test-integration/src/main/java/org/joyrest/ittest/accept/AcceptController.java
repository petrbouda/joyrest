package org.joyrest.ittest.accept;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

import java.util.Date;

import static java.util.Objects.requireNonNull;
import static org.joyrest.model.http.HttpStatus.*;
import static org.joyrest.model.http.MediaType.*;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class AcceptController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        FeedEntry f = new FeedEntry();
        f.setLink("http://localhost:8080");
        f.setPublishDate(new Date());
        f.setTitle("My Feed Title");
        f.setDescription("My Feed Description");

        setGlobalPath("/ittest/accept");

        post("/wildcard", (req, resp) -> {
            resp.status(NO_CONTENT);
        }).produces(MediaType.WILDCARD);

        post("/app-json", (req, resp) -> {
            resp.status(OK);
            resp.entity(f);
        }, Resp(FeedEntry.class))
                .produces(JSON);

        post("/app-json-xml", (req, resp) -> {
            requireNonNull(req.getEntity(), "No entity added into the route");
            resp.status(CREATED);
            resp.entity(f);
        }, Req(FeedEntry.class), Resp(FeedEntry.class))
                .produces(JSON, XML).consumes(JSON, XML, PLAIN_TEXT);

        get((req, resp) -> {
            resp.entity(f);
            resp.status(OK);
        }, Resp(FeedEntry.class))
                .produces(MediaType.JSON);
    }
}
