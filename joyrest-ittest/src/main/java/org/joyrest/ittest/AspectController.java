package org.joyrest.ittest;

import org.joyrest.ittest.interceptor.TestAspect;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

public class AspectController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("/ittest/aspect");

		post((req, resp) -> {
			resp.status(HttpStatus.OK);
		}).consumes(MediaType.JSON).aspect(new TestAspect());
	}
}
