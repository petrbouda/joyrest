package org.joyrest.ittest.routes;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

public class RequestResponseDataController extends TypedControllerConfiguration {

	@Override
	protected void configure() {
		setGlobalPath("feeds");

		post((request, response) -> {
			
			
			assertNotNull("Request object cannot be null.", request);
			assertNotNull("Response object cannot be null.", response);
			assertNotNull("Entity object cannot be null.", entry);

			assertThat("Entity object is not the same object as in Request#getEntityClass()",
				request.getEntity(), sameInstance(entry));
			assertEquals("Http Method is not correct.",
				HttpMethod.POST, request.getMethod());
			assertEquals("Route path is not correct.",
				"/feeds", request.getPath());
			assertEquals("Content-Type header value is not correct.",
				MediaType.JSON.getValue(), request.getHeader(HeaderName.CONTENT_TYPE).get());
			assertEquals("Accept header value is not correct.",
				MediaType.WILDCARD.getValue(), request.getHeader(HeaderName.ACCEPT).get());
			assertThat("Invalid values in path parts",
				request.getPathParts(), contains("feeds"));
			assertTrue("There is any path param and should not be.",
				request.getPathParams().isEmpty());
			assertTrue("There is any query param and should not be.",
				request.getQueryParams().isEmpty());
		}, Entry.class).consumes(MediaType.JSON);
	}
}
