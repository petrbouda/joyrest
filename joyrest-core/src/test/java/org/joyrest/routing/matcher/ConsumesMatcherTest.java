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
import static org.junit.Assert.assertTrue;

public class ConsumesMatcherTest {

    private static InternalRoute basicRoute() {
        RouteAction<Request<?>, Response<?>> action =
            (req, resp) -> resp.status(HttpStatus.CONFLICT);

        return new InternalRoute("/path", HttpMethod.POST, action, null, null);
    }

    @Test
    public void consumes_match_route_wildcard() throws Exception {
        InternalRoute route = basicRoute();
        route.consumes(WILDCARD);

        RequestStub req = new RequestStub();

        boolean result = RequestMatcher.matchConsumes(route, req);
        assertTrue(result);
    }

    @Test
    public void consumes_match_route_json() throws Exception {
        InternalRoute route = basicRoute();
        route.consumes(JSON);

        RequestStub req = new RequestStub();
        req.setContentType(JSON);

        boolean result = RequestMatcher.matchConsumes(route, req);
        assertTrue(result);
    }

    @Test
    public void consumes_match_route_multiple() throws Exception {
        InternalRoute route = basicRoute();
        route.consumes(JSON, XML, PLAIN_TEXT);

        RequestStub req = new RequestStub();
        req.setContentType(XML);

        boolean result = RequestMatcher.matchConsumes(route, req);
        assertTrue(result);
    }
}