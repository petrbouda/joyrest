package org.joyrest.routing;

import org.joyrest.exception.type.InvalidConfigurationException;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Class which customizes the form of the path to a valid internal representation
 *
 * "//"		=> Throw an Exception
 * "/" 		=> Correct!
 * ""  		=> Add "/"
 * "/path" 	=> Correct!
 * "/path/"	=> Remove the last char "/"
 * "path	=> Add the first char "/"
 * "path/"	=> Add the first char "/" && Remove the last char "/"
 **/
public class PathCorrector implements Function<String, String> {

	private static final String SLASH = "/";

	private static final String BLANK_PATH = "";

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

	private static String removeLastChar(String str) {
		return str.substring(0,str.length()-1);
	}
}
