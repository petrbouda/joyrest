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
package org.joyrest.common;

import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Test;
import static org.joyrest.common.collection.UnmodifiableMapCollector.toUnmodifiableMap;
import static org.junit.Assert.assertEquals;

import static java.util.function.Function.identity;

public class UnmodifiableMapCollectorTest {

    @Test
    public void testToUnmodifiableMap() {
        Map<Integer, Integer> map = IntStream.rangeClosed(1, 4)
            .mapToObj(Integer::valueOf)
            .collect(toUnmodifiableMap(identity(), identity()));

        assertEquals(4, map.size());
        assertEquals(map.get(1), Integer.valueOf(1));
        assertEquals(map.get(2), Integer.valueOf(2));
        assertEquals(map.get(3), Integer.valueOf(3));
        assertEquals(map.get(4), Integer.valueOf(4));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testToUnmodifiableMapException() {
        Map<Integer, Integer> map = IntStream.rangeClosed(1, 4)
            .mapToObj(Integer::valueOf)
            .collect(toUnmodifiableMap(identity(), identity()));

        map.put(5, 5);
    }

}