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
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PathCorrectorTest {

    private final PathCorrector testedClass = new PathCorrector();

    @Test
    public void null_path() {
        String result = testedClass.apply(null);
        assertEquals("/", result);
    }

    @Test(expected = InvalidConfigurationException.class)
    public void contains_double_slash() {
        testedClass.apply("/services//jokes");
    }

    @Test
    public void contains_single_slash() {
        String result = testedClass.apply("/");
        assertEquals("/", result);
    }

    @Test
    public void contains_blank_Path() {
        String result = testedClass.apply("");
        assertEquals("/", result);
    }

    @Test
    public void contains_starts_slash() {
        String result = testedClass.apply("/services/jokes/path");
        assertEquals("/services/jokes/path", result);
    }

    @Test
    public void contains_both_slash() {
        String result = testedClass.apply("/services/jokes/path/");
        assertEquals("/services/jokes/path", result);
    }

    @Test
    public void contains_both_without_slash() {
        String result = testedClass.apply("services/jokes/path");
        assertEquals("/services/jokes/path", result);
    }

    @Test
    public void contains_ends_slash() {
        String result = testedClass.apply("services/jokes/path/");
        assertEquals("/services/jokes/path", result);
    }
}