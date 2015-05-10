/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joyrest.extractor.param.StringPath;
import org.joyrest.model.RoutePart;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * Utility class which includes methods around the path
 *
 * @author pbouda
 */
public final class PathUtils {

	public static List<String> createPathParts(String path) {
		return Stream.of(path.split("/"))
			.map(String::trim)
			.filter(PathUtils::isNotEmpty)
			.collect(toList());
	}

	public static List<RoutePart<String>> createRouteParts(String path) {
		return createPathParts(path).stream()
			.map(part -> new RoutePart<>(RoutePart.Type.PATH, part, StringPath.INSTANCE))
			.collect(toList());
	}

	private static boolean isNotEmpty(String value) {
		return nonNull(value) && !value.isEmpty();
	}

}
