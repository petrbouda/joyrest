package org.joyrest.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joyrest.extractor.param.StringPath;
import org.joyrest.model.RoutePart;

/**
 * @author pbouda
 */
public final class PathUtils {

	/**
	 * Creates a list of the parts from the provided path which is splitted up by a slash char
	 *
	 * @return list of path's parts which are splitted up by a slash char
	 */
	public static List<String> createPathParts(String path) {
		return Stream.of(path.split("/"))
			.map(String::trim)
			.filter(PathUtils::isNotEmpty)
			.collect(Collectors.toList());
	}

	public static List<RoutePart<String>> createRouteParts(String path) {
		List<String> pathParts = createPathParts(path);

		return pathParts.stream()
			.map(part -> new RoutePart<>(RoutePart.Type.PATH, part, StringPath.INSTANCE))
			.collect(Collectors.toList());
	}

	private static boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

}
