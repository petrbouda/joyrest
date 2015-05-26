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
package org.joyrest.routing;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import org.joyrest.exception.type.InvalidConfigurationException;

/**
 * Class which customizes the form of the path to a valid internal representation
 *
 * <p>
 * "//" Throw an Exception
 * </p>
 * <p>
 * "/" Correct!
 * </p>
 * <p>
 * "" Add "/"
 * </p>
 * <p>
 * "/path" Correct!
 * </p>
 * <p>
 * "/path/" Remove the last char "/"
 * </p>
 * <p>
 * "path" Add the first char "/"
 * </p>
 * <p>
 * "path/" Add the first char "/", Remove the last char "/"
 * </p>
 *
 * @author pbouda
 **/
public class PathCorrector implements Function<String, String> {

	private static final String SLASH = "/";

	private static final String BLANK_PATH = "";

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	@Override
	public String apply(final String path) {
		if (isNull(path))
			return SLASH;

		if (path.contains("//"))
			throw new InvalidConfigurationException("Route's path cannot contain string '//'");

		if (SLASH.equals(path))
			return path;

		if (BLANK_PATH.equals(path))
			return SLASH;

		if (path.startsWith(SLASH) && !path.endsWith(SLASH))
			return path;

		if (path.startsWith(SLASH) && path.endsWith(SLASH))
			return removeLastChar(path);

		if (!path.startsWith(SLASH) && !path.endsWith(SLASH))
			return SLASH + path;

		if (!path.startsWith(SLASH) && path.endsWith(SLASH))
			return SLASH + removeLastChar(path);

		return path;
	}
}
