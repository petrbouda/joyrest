package org.joyrest.ittest.params;

import java.util.Date;

import org.joyrest.exception.type.RestException;
import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.joyrest.model.http.HttpStatus.OK;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class ParamsController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/params");

        FeedEntry f = new FeedEntry();
        f.setLink("http://localhost:8080");
        f.setPublishDate(new Date());
        f.setTitle("My Feed Title");
        f.setDescription("My Feed Description");

        get("/{id}", (req, resp) -> {
            String id = req.getPathParam("id");
            if (!"1".equals(id)) {
                throw new RestException(HttpStatus.BAD_REQUEST);
            }

            f.setDescription("ID path param = " + id);
            resp.entity(f);
            resp.status(OK);
        }, Resp(FeedEntry.class)).produces(JSON);

        get("/int/{id:int}", (req, resp) -> {
            String id = req.getPathParam("id");
            if (!"2".equals(id)) {
                throw new RestException(HttpStatus.BAD_REQUEST);
            }

            f.setDescription("ID path param = " + id);
            resp.entity(f);
            resp.status(OK);
        }, Resp(FeedEntry.class)).produces(JSON);

        get("/int/{first_id:int}/next/{second_id:str}", (req, resp) -> {
            String first = req.getPathParam("first_id");
            String second = req.getPathParam("second_id");
            if (!"1".equals(first) || !"2".equals(second)) {
                throw new RestException(HttpStatus.BAD_REQUEST);
            }

            f.setDescription("ID path params = " + first + " and " + second);
            resp.entity(f);
            resp.status(OK);
        }, Resp(FeedEntry.class)).produces(JSON);
    }
}
