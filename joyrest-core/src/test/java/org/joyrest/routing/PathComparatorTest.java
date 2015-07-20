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
package org.joyrest.routing;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.stubs.RequestStub;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathComparatorTest {

    private final PathComparator testedClass = new PathComparator();

    private static InternalRoute basicRoute(String path) {
        RouteAction<Request<?>, Response<?>> action =
            (req, resp) -> resp.status(HttpStatus.CONFLICT);

        return new InternalRoute(path, HttpMethod.POST, action, null, null);
    }

    @Test
    public void same_path() {
        InternalRoute route = basicRoute("/services/jokes/single");
        RequestStub req = new RequestStub();
        req.setPath("/services/jokes/single");

        boolean result = testedClass.test(route, req);
        assertTrue(result);
    }

    @Test
    public void with_params() {
        InternalRoute route = basicRoute("/services/jokes/{joke_id}/paragraph/{par_id}");
        RequestStub req = new RequestStub();
        req.setPath("/services/jokes/1/paragraph/15");

        boolean result = testedClass.test(route, req);
        assertTrue(result);
    }

    @Test
    public void wrong_part_count() {
        InternalRoute route = basicRoute("/services/jokes/{joke_id}");
        RequestStub req = new RequestStub();
        req.setPath("/services/jokes/1/paragraph/15");

        boolean result = testedClass.test(route, req);
        assertFalse(result);
    }

    @Test
    public void not_same() {
        InternalRoute route = basicRoute("/services/jokes");
        RequestStub req = new RequestStub();
        req.setPath("/services/jokes/single");

        boolean result = testedClass.test(route, req);
        assertFalse(result);
    }

}