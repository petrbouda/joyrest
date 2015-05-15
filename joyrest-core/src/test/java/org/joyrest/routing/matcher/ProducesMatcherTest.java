package org.joyrest.routing.matcher;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.joyrest.model.http.MediaType.*;
import static org.junit.Assert.*;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.RouteAction;
import org.joyrest.stubs.RequestStub;
import org.junit.Test;

public class ProducesMatcherTest {

	@Test
	public void produces_match_wildcards() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(WILDCARD);

		RequestStub req = new RequestStub();
		req.setAccept(singletonList(WILDCARD));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(WILDCARD, req.getMatchedAccept());
	}

	@Test
	public void produces_match_route_wildcard() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(WILDCARD);

		RequestStub req = new RequestStub();
		req.setAccept(asList(JSON, XML, PLAIN_TEXT));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(JSON, req.getMatchedAccept());
	}

	@Test
	public void produces_match_request_wildcard() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(JSON);

		RequestStub req = new RequestStub();
		req.setAccept(singletonList(WILDCARD));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(JSON, req.getMatchedAccept());
	}

	@Test
	public void produces_match_json() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(JSON);

		RequestStub req = new RequestStub();
		req.setAccept(singletonList(JSON));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(JSON, req.getMatchedAccept());
	}

	@Test
	public void produces_match_route_multiple() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(JSON, XML, PLAIN_TEXT);

		RequestStub req = new RequestStub();
		req.setAccept(singletonList(JSON));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(JSON, req.getMatchedAccept());
	}

	@Test
	public void produces_match_request_multiple() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(JSON);

		RequestStub req = new RequestStub();
		req.setAccept(asList(JSON, XML, PLAIN_TEXT));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertTrue(result);
		assertEquals(JSON, req.getMatchedAccept());
	}

	@Test
	public void produces_does_not_match() throws Exception {
		InternalRoute route = basicRoute();
		route.produces(JSON);

		RequestStub req = new RequestStub();
		req.setAccept(singletonList(XML));

		boolean result = RequestMatcher.matchProduces(route, req);
		assertFalse(result);
	}

	private static InternalRoute basicRoute() {
		RouteAction<Request<?>, Response<?>> action =
				(req, resp) -> resp.status(HttpStatus.CONFLICT);

		return new InternalRoute("/path", HttpMethod.POST, action, null, null);
	}
}