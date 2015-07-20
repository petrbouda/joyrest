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
package org.joyrest.context.autoconfigurar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joyrest.context.initializer.Initializer;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.transform.AbstractReaderWriter;
import org.joyrest.transform.StringReaderWriter;

import static java.lang.String.format;

public class AutoConfigurer {

    private static final JoyLogger log = new JoyLogger(AutoConfigurer.class);

    public static List<AbstractReaderWriter> configureReadersWriters() {
        List<AbstractReaderWriter> list = new ArrayList<>();
        list.add(new StringReaderWriter());

        getClass("org.joyrest.gson.GsonReaderWriter").ifPresent(gson -> processBean(gson, list));
        getClass("org.joyrest.hessian.HessianReaderWriter").ifPresent(hessian -> processBean(hessian, list));
        getClass("org.joyrest.jackson.JacksonReaderWriter").ifPresent(jackson -> processBean(jackson, list));
        return list;
    }

    public static List<Initializer> configureInitializers() {
        List<Initializer> list = new ArrayList<>();
        getClass("org.joyrest.oauth2.initializer.OAuth2Initializer").ifPresent(initializer -> processBean(initializer, list));
        return list;
    }

    @SuppressWarnings("unchecked")
    private static <B> void processBean(Class<?> clazz, List<B> list) {
        try {
            list.add((B) clazz.newInstance());
            log.debug(() -> String.format("Class '%s' was added using auto-configuration.", clazz));
        } catch (Exception e) {
            throw new InvalidConfigurationException(format("Class %s is not valid.", clazz));
        }
    }

    protected static Optional<Class<?>> getClass(String className) {
        try {
            ClassLoader classLoader = AutoConfigurer.class.getClassLoader();
            return Optional.of(classLoader.loadClass(className));
        } catch (Throwable ex) {
            return Optional.empty();
        }
    }
}
