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

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.gson.GsonReaderWriter;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public class GuiceApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ControllerConfiguration> controllerBinder = newSetBinder(binder(), ControllerConfiguration.class);
        controllerBinder.addBinding().to(JokeController.class).in(Singleton.class);

        GsonReaderWriter readerWriter = new GsonReaderWriter();
        Multibinder<Writer> writerBinder = newSetBinder(binder(), Writer.class);
        writerBinder.addBinding().toInstance(readerWriter);

        Multibinder<Reader> readerBinder = newSetBinder(binder(), Reader.class);
        readerBinder.addBinding().toInstance(readerWriter);

        bind(JokeService.class)
            .to(JokeServiceImpl.class)
            .in(Singleton.class);
    }
}
