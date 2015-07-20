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
package org.joyrest.model.http;

import java.util.List;

import org.joyrest.exception.type.RestException;
import org.junit.Test;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.XML;
import static org.junit.Assert.assertEquals;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class MediaTypeTest {

    @Test
    public void media_type_null_and_empty() {
        MediaType mediaType = MediaType.of(null);
        assertEquals(MediaType.WILDCARD, mediaType);

        MediaType mediaType2 = MediaType.of("");
        assertEquals(MediaType.WILDCARD, mediaType2);
    }

    @Test
    public void content_type_without_charset() {
        MediaType contentType = MediaType.of("text/html");

        assertEquals("text/html", contentType.get());
        assertEquals("text", contentType.getType());
        assertEquals("html", contentType.getSubType());
        assertEquals(0, contentType.getParams().size());
    }

    @Test
    public void content_type_with_charset() {
        MediaType contentType = MediaType.of("text/html; charset=utf-8");

        assertEquals("text/html", contentType.get());
        assertEquals("text", contentType.getType());
        assertEquals("html", contentType.getSubType());
        assertEquals(1, contentType.getParams().size());
        assertEquals("utf-8", contentType.getParam("charset").get());
    }

    @Test
    public void content_type_with_processing_type() {
        MediaType contentType = MediaType.of("application/vnd.github+json");

        assertEquals("application/vnd.github+json", contentType.get());
        assertEquals("application", contentType.getType());
        assertEquals("vnd.github+json", contentType.getSubType());
        assertEquals("application/json", contentType.getProcessingType().get().get());
    }

    @Test(expected = RestException.class)
    public void media_type_wrong_one_part() {
        MediaType.of("application");
    }

    @Test(expected = RestException.class)
    public void media_type_wrong_three_parts() {
        MediaType.of("application/json/xml");
    }

    @Test
    public void list_success() {
        List<MediaType> mediaTypes = MediaType.list("application/json, application/xml");
        assertEquals(asList(JSON, XML), mediaTypes);
    }

    @Test
    public void list_duplicate() {
        List<MediaType> mediaTypes = MediaType.list("application/json, application/xml, application/json");
        assertEquals(asList(JSON, XML), mediaTypes);
    }

    @Test
    public void list_with_blank_spaces() {
        List<MediaType> mediaTypes = MediaType.list("application/json, , application/json");
        assertEquals(singletonList(JSON), mediaTypes);
    }

}