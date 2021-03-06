/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing.matcher;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.RouteAction;
import org.joyrest.stubs.RequestStub;
import org.junit.Test;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.PLAIN_TEXT;
import static org.joyrest.model.http.MediaType.WILDCARD;
import static org.joyrest.model.http.MediaType.XML;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ProducesMatcherTest {

    private static InternalRoute basicRoute() {
        RouteAction<Request<?>, Response<?>> action =
            (req, resp) -> resp.status(HttpStatus.CONFLICT);

        return new InternalRoute("/path", HttpMethod.POST, action, null, null);
    }

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
}