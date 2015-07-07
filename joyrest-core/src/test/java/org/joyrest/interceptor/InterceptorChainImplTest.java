package org.joyrest.interceptor;

import org.joyrest.interceptor.aspects.FirstInterceptor;
import org.joyrest.interceptor.aspects.SecondInterceptor;
import org.joyrest.interceptor.aspects.SwallowInterceptor;
import org.joyrest.interceptor.aspects.ThirdInterceptor;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InterceptorChainImplTest {

    private static InternalRoute basicRoute() {
        RouteAction<Request<?>, Response<?>> action =
            (req, resp) -> resp.status(HttpStatus.CONFLICT);

        return new InternalRoute("", HttpMethod.POST, action, null, null);
    }

    @Test
    public void success_proceed() throws Exception {
        InternalRoute route = basicRoute();
        route.interceptor(new FirstInterceptor());
        route.interceptor(new SecondInterceptor());
        route.interceptor(new ThirdInterceptor());

        InternalRequest<Object> request = new RequestStub();
        InternalResponse<Object> response = new ResponseStub();

        InterceptorChain chain = new InterceptorChainImpl(route);
        chain.proceed(request, response);

        assertEquals(0, request.getHeaders().size());
        assertEquals(HttpStatus.CONFLICT, response.getStatus());
    }

    @Test
    public void swallowed_proceed() throws Exception {
        InternalRoute route = basicRoute();
        route.interceptor(new SwallowInterceptor());

        InternalRequest<Object> request = new RequestStub();
        InternalResponse<Object> response = new ResponseStub();

        InterceptorChain chain = new InterceptorChainImpl(route);
        chain.proceed(request, response);

        assertEquals("YES", request.getHeaders().get(HeaderName.of("Swallowed")));
        assertNull(response.getStatus());
    }
}