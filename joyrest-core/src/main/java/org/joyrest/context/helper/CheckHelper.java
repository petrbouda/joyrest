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
package org.joyrest.context.helper;

import java.util.Collection;
import java.util.Set;

import org.joyrest.common.annotation.Ordered;
import org.joyrest.exception.type.InvalidConfigurationException;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public final class CheckHelper {

    public static <T extends Ordered> void orderDuplicationCheck(Collection<T> collection) {
        Set<T> duplicated = collection.stream()
            .filter(n -> collection.stream()
                .filter(x -> x.getOrder() == n.getOrder()).count() > 1)
            .collect(toSet());

        if (!duplicated.isEmpty()) {
            String duplicatedOrders = duplicated.stream()
                .map(i -> String.valueOf(i.getOrder()))
                .collect(joining(", "));

            throw new InvalidConfigurationException(
                "There is registered more than one entity with the given order: " + duplicatedOrders);
        }
    }
}
