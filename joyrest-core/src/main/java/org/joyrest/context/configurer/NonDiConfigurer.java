/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.context.configurer;

import java.util.List;

import org.joyrest.context.ApplicationContext;

import static java.util.Objects.requireNonNull;

/**
 * Configurer which is mainly used for an implementation of a new configurer that is not based on any Dependency Injection
 * Framework.
 *
 * {@inheritDoc}
 */
public class NonDiConfigurer extends AbstractConfigurer<ApplicationConfiguration> {

    private ApplicationConfiguration context;

    @Override
    @SuppressWarnings("unchecked")
    public <B> List<B> getBeans(Class<B> clazz) {
        return (List<B>) context.get(clazz);
    }

    @Override
    public ApplicationContext initialize(ApplicationConfiguration context) {
        requireNonNull(context, "ApplicationContext cannot be null.");

        this.context = context;
        return initializeContext();
    }

}
