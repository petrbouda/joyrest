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
package org.joyrest.hk2;

import java.util.List;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.AbstractConfigurer;

import static java.util.Objects.requireNonNull;

/**
 * Class that is able to configure JoyREST Framework for HK2 Dependency Injection Framework.
 *
 * @author pbouda
 */
public final class HK2Configurer extends AbstractConfigurer<Binder> {

    public static final String JOYREST_BEAN_CONTEXT = "JoyrestContext";

    private ServiceLocator locator = null;

    @Override
    public <B> List<B> getBeans(Class<B> clazz) {
        return locator.getAllServices(clazz);
    }

    @Override
    public ApplicationContext initialize(Binder applicationConfig) {
        requireNonNull(applicationConfig, "ApplicationConfig must be non-null for configuring HK2.");

        ServiceLocatorFactory locatorFactory = ServiceLocatorFactory.getInstance();
        locator = locatorFactory.create(JOYREST_BEAN_CONTEXT);
        ServiceLocatorUtilities.bind(locator, applicationConfig);

        return initializeContext();
    }

}
