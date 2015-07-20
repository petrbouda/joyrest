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
package org.joyrest.utils;

import java.util.List;

import org.joyrest.extractor.param.StringVariable;
import org.joyrest.model.RoutePart;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class PathUtilsTest {

    @Test
    public void path_multiple_parts() throws Exception {
        List<String> parts = PathUtils.createPathParts("/service/jokes/single");
        assertEquals(asList("service", "jokes", "single"), parts);
    }

    @Test
    public void path_one_part() throws Exception {
        List<String> parts = PathUtils.createPathParts("/service");
        assertEquals(singletonList("service"), parts);
    }

    @Test
    public void path_null_path() throws Exception {
        List<String> parts = PathUtils.createPathParts(null);
        assertEquals(emptyList(), parts);
    }

    @Test
    public void path_double_slash() throws Exception {
        List<String> parts = PathUtils.createPathParts("/service//jokes");
        assertEquals(asList("service", "jokes"), parts);
    }

    @Test
    public void path_whitespace() throws Exception {
        List<String> parts = PathUtils.createPathParts("/service/ jokes ");
        assertEquals(asList("service", "jokes"), parts);
    }

    @Test
    public void route_null_path() throws Exception {
        List<RoutePart<String>> parts = PathUtils.createRoutePathParts(null);
        assertEquals(emptyList(), parts);
    }

    @Test
    public void route_multiple_parts() throws Exception {
        List<RoutePart<String>> routePathParts = PathUtils.createRoutePathParts("/service/jokes/single");
        assertEquals(asList(
            new RoutePart<>(RoutePart.Type.PATH, "service", StringVariable.INSTANCE),
            new RoutePart<>(RoutePart.Type.PATH, "jokes", StringVariable.INSTANCE),
            new RoutePart<>(RoutePart.Type.PATH, "single", StringVariable.INSTANCE)), routePathParts);
    }
}