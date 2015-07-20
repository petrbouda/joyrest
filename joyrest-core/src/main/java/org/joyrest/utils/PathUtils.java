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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import org.joyrest.extractor.PathParamExtractor;
import org.joyrest.extractor.param.StringVariable;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.PathParam;
import org.joyrest.routing.InternalRoute;

import com.codepoetics.protonpack.StreamUtils;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Utility class which includes methods around the path
 *
 * @author pbouda
 */
public final class PathUtils {

    /* Class is able to extract path params from incoming path according an info from route */
    private static final PathParamExtractor pathParamExtractor = new PathParamExtractor();

    public static Map<String, PathParam> getPathParams(InternalRoute route, List<String> pathParts) {
        return StreamUtils
            .zip(route.getRouteParts().stream(), pathParts.stream(), pathParamExtractor)
            .filter(Objects::nonNull)
            .collect(toMap(PathParam::getName, identity()));
    }

    public static List<String> createPathParts(String path) {
        if (isNull(path)) {
            return emptyList();
        }

        List<String> parts = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (isNotEmpty(token)) {
                parts.add(token);
            }
        }
        return parts;
    }

    public static List<RoutePart<String>> createRoutePathParts(String path) {
        if (isNull(path)) {
            return emptyList();
        }

        return createPathParts(path).stream()
            .map(part -> new RoutePart<>(RoutePart.Type.PATH, part, StringVariable.INSTANCE))
            .collect(toList());
    }

    private static boolean isNotEmpty(String value) {
        return nonNull(value) && !value.isEmpty();
    }

}
