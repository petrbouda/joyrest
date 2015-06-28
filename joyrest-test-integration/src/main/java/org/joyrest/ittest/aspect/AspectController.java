package org.joyrest.ittest.aspect;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class AspectController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setControllerPath("/ittest/interceptor");

		get((req, resp) -> resp.status(HttpStatus.OK));
	}
}
