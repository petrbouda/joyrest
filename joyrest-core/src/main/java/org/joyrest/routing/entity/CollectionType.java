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
package org.joyrest.routing.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionType<T> extends Type<T> {

    private final Class<?> param;

    public CollectionType(Class<?> type, Class<?> param) {
        super(type);
        this.param = param;
    }

    public static <P> CollectionType<List<P>> List(Class<P> param) {
        return new CollectionType<>(List.class, param);
    }

    public static <P> CollectionType<Set<P>> Set(Class<P> param) {
        return new CollectionType<>(Set.class, param);
    }

    public static <P> CollectionType<Collection<P>> Col(Class<P> param) {
        return new CollectionType<>(Collection.class, param);
    }

    public Class<?> getParam() {
        return param;
    }

    public String toString() {
        return getType().getSimpleName() + "<" + param.getSimpleName() + ">";
    }
}
