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
package org.joyrest.examples.servlet.jokeapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.joyrest.routing.ControllerConfiguration;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(JokeServiceImpl.class)
            .to(new TypeLiteral<JokeService>() {})
            .in(Singleton.class);

        bind(JokeController.class)
            .to(ControllerConfiguration.class)
            .in(Singleton.class);
    }
}
