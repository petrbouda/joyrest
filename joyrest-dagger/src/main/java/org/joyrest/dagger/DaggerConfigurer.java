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
package org.joyrest.dagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.AbstractConfigurer;
import org.joyrest.dagger.template.ConfigurationTemplate;
import org.joyrest.logging.JoyLogger;

import static java.util.Collections.emptyList;

public class DaggerConfigurer extends AbstractConfigurer<Object> {

    private static final JoyLogger log = new JoyLogger(DaggerConfigurer.class);

    @SuppressWarnings("rawtypes")
    private final Map<Class, Supplier> beanFactory = new HashMap<>();

    public ApplicationContext initialize() {
        return initializeContext();
    }

    @Override
    public ApplicationContext initialize(Object nullConfiguration) {
        throw new UnsupportedOperationException("This method is not supported for initialization of Dagger application, " +
            "use DaggerConfigurer#initialize() method instead.");
    }

    public <B> DaggerConfigurer addSupplier(Class<B> clazz, Supplier<Set<B>> supplier) {
        beanFactory.put(clazz, supplier);
        return this;
    }

    public DaggerConfigurer addDaggerTemplate(ConfigurationTemplate template) {
        template.getSuppliers().forEach(beanFactory::putIfAbsent);
        return this;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <B> List<B> getBeans(Class<B> clazz) {
        try {
            return (List<B>) new ArrayList((Set) beanFactory.get(clazz).get());
        } catch (NullPointerException npe) {
            log.warn(() -> "There is no registered bean of the type " + clazz.getSimpleName());
            return emptyList();
        }
    }
}
