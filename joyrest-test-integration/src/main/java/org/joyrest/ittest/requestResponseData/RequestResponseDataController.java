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
package org.joyrest.ittest.requestResponseData;

import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.TypedControllerConfiguration;
import static org.hamcrest.Matchers.sameInstance;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RequestResponseDataController extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("feeds");

        post((request, response) -> {
            FeedEntry entry = request.getEntity();

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
                MediaType.JSON, MediaType.of(request.getHeader(HeaderName.CONTENT_TYPE).get()));
            assertEquals("Accept header value is not correct.",
                MediaType.WILDCARD, MediaType.of(request.getHeader(HeaderName.ACCEPT).get()));
            assertTrue("There is any path param and should not be.",
                request.getPathParams().isEmpty());
            assertTrue("There is any query param and should not be.",
                request.getQueryParams().isEmpty());

            response.status(HttpStatus.NO_CONTENT);
        }, Req(FeedEntry.class), Resp(FeedEntry.class))
            .consumes(MediaType.JSON);
    }
}
