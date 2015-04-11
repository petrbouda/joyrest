package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.AbstractControllerConfiguration;
import org.joyrest.routing.TypedControllerConfiguration;

public class RouteController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/route");

		/*
		 * POST ROUTES
		 **/

		// Missing POST -> no path, no body

//		post((req, resp, body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		post("/withBody", (Request req, Response resp, FeedEntry body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		post("/withSpecifiedBody", (req, resp, body) -> {
//			resp.status(HttpStatus.OK);
//		}, FeedEntry.class).consumes(MediaType.JSON);
//
//		post("/withoutBody", (req, resp) -> {
//			resp.status(HttpStatus.OK);
//		});
//
//		/*
//		 * GET ROUTES
//		 **/
//
//		// Missing GET -> no path, no body
//
//		get((Request req, Response resp, FeedEntry body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		get("/withBody", (Request req, Response resp, FeedEntry body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		get("/withSpecifiedBody", (req, resp, body) -> {
//			resp.status(HttpStatus.OK);
//		}, FeedEntry.class).consumes(MediaType.JSON);
//
//		get("/withoutBody", (req, resp) -> {
//			resp.status(HttpStatus.OK);
//		});
//
//		/*
//		 * PUT ROUTES
//		 **/
//
//		// Missing PUT -> no path, no body
//
//		put((Request req, Response resp, FeedEntry body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		put("/withBody", (Request req, Response resp, FeedEntry body) -> {
//			resp.status(HttpStatus.OK);
//		}).consumes(MediaType.JSON);
//
//		put("/withSpecifiedBody", (req, resp, body) -> {
//			resp.status(HttpStatus.OK);
//		}, FeedEntry.class).consumes(MediaType.JSON);
//
//		put("/withoutBody", (req, resp) -> {
//			resp.status(HttpStatus.OK);
//		});
//
//		/*
//		 * DELETE ROUTES
//		 **/
//
//		delete((req, resp) -> {
//			resp.status(HttpStatus.OK);
//		});
//
//		delete("/withoutBody", (req, resp) -> {
//			resp.status(HttpStatus.OK);
//		});
	}


}
