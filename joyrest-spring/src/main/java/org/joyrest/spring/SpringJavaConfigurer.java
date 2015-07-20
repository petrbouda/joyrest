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
package org.joyrest.spring;

import java.util.ArrayList;
import java.util.List;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.AbstractConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

/**
 * Class that is able to configure JoyREST Framework for Spring Dependency Injection Framework.
 *
 * @author pbouda
 */
public final class SpringJavaConfigurer extends AbstractConfigurer<Object> {

    private org.springframework.context.ApplicationContext context = null;

    @Override
    public ApplicationContext initialize(Object applicationConfig) {
        requireNonNull(applicationConfig, "ApplicationConfig must be non-null for configuring Spring.");

        boolean isConfigAnnotated = applicationConfig.getClass().isAnnotationPresent(Configuration.class);
        if (!isConfigAnnotated) {
            throw new IllegalArgumentException("Provided config is @Configuration annotated Spring Java Config");
        }

        context = new AnnotationConfigApplicationContext(applicationConfig.getClass());
        return initializeContext();
    }

    @Override
    public <B> List<B> getBeans(Class<B> clazz) {
        return new ArrayList<>(context.getBeansOfType(clazz).values());
    }
}
