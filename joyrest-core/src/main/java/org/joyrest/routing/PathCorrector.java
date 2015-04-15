package org.joyrest.routing;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import org.joyrest.exception.type.InvalidConfigurationException;

/**
 * Class which customizes the form of the path to a valid internal representation
 *
 * "//" => Throw an Exception "/" => Correct! "" => Add "/" "/path" => Correct! "/path/" => Remove the last char "/"
 * "path	=> Add the first char "/" "path/" => Add the first char "/" && Remove the last char "/"
 **/
public class PathCorrector implements Function<String, String> {

	private static final String SLASH = "/";

	private static final String BLANK_PATH = "";

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	@Override
	public String apply(final String path) {
		requireNonNull(path, "A Path to correct cannot be null.");

		if (path.contains("//")) {
			throw new InvalidConfigurationException("Route's path cannot contain string '//'");
		}

		if (SLASH.equals(path)) {
			return path;
		}

		if (BLANK_PATH.equals(path)) {
			return SLASH;
		}

		if (path.startsWith(SLASH) && !path.endsWith(SLASH)) {
			return path;
		}

		if (path.startsWith(SLASH) && path.endsWith(SLASH)) {
			return removeLastChar(path);
		}

		if (!path.startsWith(SLASH) && !path.endsWith(SLASH)) {
			return SLASH + path;
		}

		if (!path.startsWith(SLASH) && path.endsWith(SLASH)) {
			return SLASH + removeLastChar(path);
		}

		return path;
	}
}
