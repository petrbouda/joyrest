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
package org.joyrest.examples.di.jokeapp.guice;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.Configurer;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.guice.GuiceConfigurer;

public class StartGuice {

    public static void main(String... args) throws Exception {
        Configurer configurer = new GuiceConfigurer();
        ApplicationContext applicationContext = configurer.initialize(new GuiceApplicationModule());
        GrizzlyServer.start(applicationContext, 5000, "/services");
    }

}
