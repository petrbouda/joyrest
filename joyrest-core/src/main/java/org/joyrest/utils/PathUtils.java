package org.joyrest.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joyrest.extractor.param.StringPath;
import org.joyrest.model.RoutePart;

import static java.util.stream.Collectors.toList;

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
		return value != null && !value.isEmpty();
	}

}
