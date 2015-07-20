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
package org.joyrest.routing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.extractor.param.IntegerVariable;
import org.joyrest.extractor.param.LongVariable;
import org.joyrest.extractor.param.StringVariable;
import org.joyrest.extractor.param.VariableType;
import org.joyrest.model.RoutePart;

/**
 * Contains a logic for parsing data from the part of the given route
 *
 * @author pbouda
 */
public class ParamParser implements Function<String, RoutePart<?>> {

    public static final String NAME_TYPE_DELIMITER = ":";
    /* This character determines whether the given part is PARAM or not */
    private static final String START_PARAM = "{";
    private static final String END_PARAM = "}";
    private static final int START_PARAM_LENGTH = START_PARAM.length();
    private static final int END_PARAM_LENGTH = END_PARAM.length();
    /* All path param types which are available for a creation of route */
    private final static Map<String, VariableType<?>> PATH_TYPES;

    static {
        PATH_TYPES = new HashMap<>();
        PATH_TYPES.put(StringVariable.NAME, StringVariable.INSTANCE);
        PATH_TYPES.put(IntegerVariable.NAME, IntegerVariable.INSTANCE);
        PATH_TYPES.put(LongVariable.NAME, LongVariable.INSTANCE);
    }

    private final Map<String, RoutePart<?>> pathParams = new HashMap<>();

    private final String path;

    public ParamParser(String path) {
        this.path = path;
    }

    private static boolean isPathParam(String part) {
        return part.startsWith(START_PARAM) && part.endsWith(END_PARAM);
    }

    private static String getPathParam(String part) {
        if (part.startsWith(START_PARAM) && part.endsWith(END_PARAM)) {
            return part.substring(START_PARAM_LENGTH, part.length() - END_PARAM_LENGTH);
        }

        throw new InvalidConfigurationException(String.format(
            "Invalid path param configurer '%s'", part));
    }

    @Override
    public RoutePart<?> apply(String part) {
        if (isPathParam(part)) {

			/* Split a name and a type of the param */
            String param = getPathParam(part);
            int paramIndex = param.indexOf(NAME_TYPE_DELIMITER);
            char[] paramChars = param.toCharArray();

			/* param index == 1 -> there is no param type definition */
            String paramName = paramIndex == -1 ? param : new String(paramChars, 0, paramIndex);

			/* Duplication param-name in one route */
            if (pathParams.containsKey(paramName)) {
                throw new InvalidConfigurationException(String.format(
                    "Route '%s' contains more path params with the same name '%s'.", path, paramName));
            }

            VariableType<?> variableType;
            /* param without a definition of a type - String default */
            if (paramIndex == -1) {
                variableType = StringVariable.INSTANCE;
            }

			/* param with a definition of a type */
            else {
                String paramType = new String(paramChars, paramIndex + 1, paramChars.length - paramIndex - 1);

                Optional<VariableType<?>> optPathType = Optional.ofNullable(PATH_TYPES.get(paramType));
                variableType = optPathType.orElseThrow(() ->
                    new InvalidConfigurationException(String.format(
                        "Missing a path type for param '%s' and type '%s' in the route '%s'.",
                        paramName, paramType, path)));
            }

            RoutePart<?> routePart = new RoutePart<>(RoutePart.Type.PARAM, paramName, variableType);
            pathParams.put(paramName, routePart);
            return routePart;
        }

        return new RoutePart<>(RoutePart.Type.PATH, part, StringVariable.INSTANCE);
    }
}
