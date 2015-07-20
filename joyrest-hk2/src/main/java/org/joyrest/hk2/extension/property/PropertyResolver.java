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
package org.joyrest.hk2.extension.property;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Singleton;

import org.glassfish.hk2.api.HK2RuntimeException;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import static java.util.Objects.isNull;

@Singleton
public class PropertyResolver implements InjectionResolver<Property> {

    private final Map<String, String> properties;
    private final Map<Type, Function<String, ?>> parsers;

    public PropertyResolver(Map<String, String> properties, Map<Type, Function<String, ?>> parsers) {
        this.properties = properties;
        this.parsers = parsers;
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> serviceHandle) {
        Property annotation = injectee.getParent().getAnnotation(Property.class);

        String property = properties.get(annotation.value());
        if (isNull(property) || property.isEmpty()) {
            throw new HK2RuntimeException(String.format("Property %s does not exist.", property));
        }

        Function<String, ?> parser = parsers.get(injectee.getRequiredType());
        return parser.apply(property);
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return true;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return true;
    }

}
