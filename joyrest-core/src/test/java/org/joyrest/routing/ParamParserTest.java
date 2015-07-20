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

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.RoutePart;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ParamParserTest {

    @Test
    public void parse_path() {
        ParamParser parser = new ParamParser("/services/joke/single");
        RoutePart<?> result = parser.apply("services");

        assertEquals("services", result.getValue());
        assertEquals("str", result.getVariableType().getName());
        assertEquals(RoutePart.Type.PATH, result.getType());
    }

    @Test
    public void parse_variable() {
        ParamParser parser = new ParamParser("/services/joke/{id}");
        RoutePart<?> result = parser.apply("{id}");

        assertEquals("id", result.getValue());
        assertEquals("str", result.getVariableType().getName());
        assertEquals(RoutePart.Type.PARAM, result.getType());
    }

    @Test
    public void parse_variable_with_type() {
        ParamParser parser = new ParamParser("/services/joke/{id:int}");
        RoutePart<?> result = parser.apply("{id:int}");

        assertEquals("id", result.getValue());
        assertEquals("int", result.getVariableType().getName());
        assertEquals(RoutePart.Type.PARAM, result.getType());
    }

    @Test(expected = InvalidConfigurationException.class)
    public void parse_variable_with_unknown_type() {
        ParamParser parser = new ParamParser("/services/joke/{id:int}");
        parser.apply("{id:unknown}");
    }

    @Test(expected = InvalidConfigurationException.class)
    public void parse_duplicate_variable() {
        ParamParser parser = new ParamParser("/services/{id}/joke/{id}");
        parser.apply("{id}");
        parser.apply("{id}");
    }
}