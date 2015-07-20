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
package org.joyrest.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CollectionUtils {

    public static boolean nonEmpty(Collection<?> col) {
        return col != null && !col.isEmpty();
    }

    public static boolean isEmpty(Collection<?> col) {
        return col != null && col.isEmpty();
    }

    public static boolean isSingletonList(Collection<?> col) {
        return col != null && col.size() == 1;
    }

    public static <T> List<T> concat(List<? extends T> a, List<? extends T> b, List<? extends T> c) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        Objects.requireNonNull(c);

        Stream<T> first = Stream.concat(a.stream(), b.stream());
        return Stream.concat(first, c.stream()).collect(toList());
    }

    public static <T> List<T> concat(List<? extends T> a, List<? extends T> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        return Stream.concat(a.stream(), b.stream()).collect(toList());
    }
}
