package org.joyrest.aspect;

import org.joyrest.aspect.aspects.FirstAspect;
import org.joyrest.aspect.aspects.SecondAspect;
import org.joyrest.aspect.aspects.SwallowAspect;
import org.joyrest.aspect.aspects.ThirdAspect;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.RouteAction;
import org.joyrest.stubs.RequestStub;
import org.joyrest.stubs.ResponseStub;
import org.junit.Test;

import static org.junit.Assert.*;

public class AspectChainImplTest {
	
	@Test
	public void success_proceed() throws Exception {
		InternalRoute route = basicRoute();
		route.aspect(new FirstAspect());
		route.aspect(new SecondAspect());
		route.aspect(new ThirdAspect());

		InternalRequest<Object> request = new RequestStub();
		InternalResponse<Object> response = new ResponseStub();

		AspectChain chain = new AspectChainImpl(route);
		chain.proceed(request, response);

		assertEquals(0, request.getHeaders().size());
		assertEquals(HttpStatus.CONFLICT, response.getStatus());
	}

	@Test
	public void swallowed_proceed() throws Exception {
		InternalRoute route = basicRoute();
		route.aspect(new SwallowAspect());

		InternalRequest<Object> request = new RequestStub();
		InternalResponse<Object> response = new ResponseStub();

		AspectChain chain = new AspectChainImpl(route);
		chain.proceed(request, response);

		assertEquals("YES", request.getHeaders().get(HeaderName.of("Swallowed")));
		assertNull(response.getStatus());
	}

	private static InternalRoute basicRoute() {
		RouteAction<Request<?>, Response<?>> action =
			(req, resp) -> resp.status(HttpStatus.CONFLICT);

		return new InternalRoute("", HttpMethod.POST, action, null, null);
	}
}