package org.joyrest.context.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joyrest.context.helper.transformer.FirstReader;
import org.joyrest.context.helper.transformer.GeneralReader;
import org.joyrest.context.helper.transformer.SecondReader;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.RouteAction;
import org.joyrest.transform.Reader;
import org.junit.Test;
import static org.joyrest.context.helper.ConfigurationHelper.createTransformers;
import static org.joyrest.context.helper.PopulateHelper.populateRouteReaders;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static java.util.Collections.emptyMap;

public class PopulateHelperTest {

    private static InternalRoute basicRoute() {
        RouteAction<Request<?>, Response<?>> action =
            (req, resp) -> resp.status(HttpStatus.CONFLICT);

        return new InternalRoute("", HttpMethod.POST, action, null, null);
    }

    @Test
    public void populate_readers_null() throws Exception {
        List<Reader> readers = Arrays.asList(new FirstReader(), new SecondReader(), new GeneralReader());
        Map<Boolean, List<Reader>> transformers = createTransformers(readers);

        InternalRoute route = basicRoute();

        populateRouteReaders(transformers, route);
        assertEquals(emptyMap(), route.getReaders());
    }

    @Test
    public void populate_readers() throws Exception {
        List<Reader> readers = Arrays.asList(new FirstReader(), new SecondReader(), new GeneralReader());
        Map<Boolean, List<Reader>> transformers = createTransformers(readers);

        InternalRoute route = basicRoute();

        populateRouteReaders(transformers, route);
        assertNull(route.getReaders().get(MediaType.of("reader/FIRST")));
        assertNull(route.getReaders().get(MediaType.of("reader/SECOND")));
        assertNull(route.getReaders().get(MediaType.of("reader/GENERAL")));
    }
}