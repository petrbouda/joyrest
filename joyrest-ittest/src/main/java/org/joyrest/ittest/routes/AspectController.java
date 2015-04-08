package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.interceptor.TestAspect;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

public class AspectController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/aspect");

		post((req, resp, body) -> {
			resp.status(HttpStatus.OK);
		}).consumes(MediaType.JSON).aspect(new TestAspect());
	}
}
