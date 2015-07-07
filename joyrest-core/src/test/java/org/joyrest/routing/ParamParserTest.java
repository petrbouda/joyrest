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