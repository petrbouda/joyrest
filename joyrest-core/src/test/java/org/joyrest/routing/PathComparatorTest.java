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