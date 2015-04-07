package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.entity.FeedEntry;
import org.joyrest.ittest.routes.interceptor.TestAspect;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.AbstractControllerConfiguration;

public class AspectController extends AbstractControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/aspect");

		post((Request req, Response resp,FeedEntry body) -> {
			resp.status(HttpStatus.OK);
		}).consumes(MediaType.JSON).aspect(new TestAspect());
	}
}
