package org.joyrest.routing.matcher;

import static org.junit.Assert.assertTrue;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.RouteAction;
import org.joyrest.stubs.RequestStub;
import org.junit.Test;

public class MethodMatcherTest {

	@Test
	public void method_match() throws Exception {
		InternalRoute route = basicRoute();

		RequestStub req = new RequestStub();
		req.setMethod(HttpMethod.POST);

		boolean result = RequestMatcher.matchHttpMethod(route, req);
		assertTrue(result);
	}

	private static InternalRoute basicRoute() {
		RouteAction<Request<?>, Response<?>> action =
				(req, resp) -> resp.status(HttpStatus.CONFLICT);

		return new InternalRoute("/path", HttpMethod.POST, action, null, null);
	}
}