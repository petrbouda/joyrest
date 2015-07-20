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
package org.joyrest.examples.nondi.jokeapp;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.ApplicationConfiguration;
import org.joyrest.context.configurer.NonDiConfigurer;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.jackson.JacksonReaderWriter;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class Start {

    public static void main(String... args) throws Exception {
        JacksonReaderWriter readerWriter = new JacksonReaderWriter();
        JokeController jokeController = new JokeController(new JokeServiceImpl());

        ApplicationConfiguration beanContext = new ApplicationConfiguration();
        beanContext.add(ControllerConfiguration.class, jokeController);
        beanContext.add(Reader.class, readerWriter);
        beanContext.add(Writer.class, readerWriter);

        ApplicationContext applicationContext = new NonDiConfigurer().initialize(beanContext);
        GrizzlyServer.start(applicationContext, 5000, "/services");
    }
}
