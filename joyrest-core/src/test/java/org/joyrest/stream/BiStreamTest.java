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
package org.joyrest.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joyrest.exception.type.RestException;
import org.junit.Test;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.junit.Assert.assertEquals;

public class BiStreamTest {

    private final List<String> names = Arrays.asList("George", "Maria", "Peter");

    @Test
    public void success() {
        BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
        Optional<String> result = stream
            .throwIfNull((name, activity) -> name.equals("Maria"),
                internalServerErrorSupplier("Maria was not found."))
            .throwIfNull((name, activity) -> name.contains("ria"),
                internalServerErrorSupplier("'ria' string not found."))
            .throwIfNull((name, activity) -> name.contains("Ma"),
                internalServerErrorSupplier("'Ma' string not found."))
            .findAny();

        assertEquals("Maria", result.get());
    }

    @Test
    public void success_multiple() {
        BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
        Optional<String> result = stream
            .throwIfNull((name, activity) -> true,
                internalServerErrorSupplier("Nobody was not found."))
            .findAny();

        assertEquals("George", result.get());
    }

    @Test
    public void exception_empty_stream() {
        BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
        try {
            stream
                .throwIfNull((name, activity) -> name.equals("Maria"),
                    internalServerErrorSupplier("Maria was not found."))
                .throwIfNull((name, activity) -> name.contains("ria"),
                    internalServerErrorSupplier("'ria' string not found."))
                .throwIfNull((name, activity) -> name.contains("Some String"),
                    internalServerErrorSupplier("'Some String' string not found."))
                .findAny();
        } catch (RestException ex) {
            assertEquals("'Some String' string not found.", ex.getMessage());
        }
    }

}