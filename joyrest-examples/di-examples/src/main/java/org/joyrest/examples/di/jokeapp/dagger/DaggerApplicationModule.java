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
package org.joyrest.examples.di.jokeapp.dagger;

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.gson.GsonReaderWriter;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerApplicationModule {

    private final GsonReaderWriter jsonReaderWriter = new GsonReaderWriter();

    /* only for dagger-compiler purposes */
    @Provides(type = Provides.Type.SET)
    Interceptor nullAspect() {
        return null;
    }

    /* only for dagger-compiler purposes */
    @Provides(type = Provides.Type.SET)
    ExceptionConfiguration jokeExceptionConfiguration() {
        return null;
    }

    @Provides(type = Provides.Type.SET)
    Writer jsonWriter() {
        return jsonReaderWriter;
    }

    @Provides(type = Provides.Type.SET)
    Reader jsonReader() {
        return jsonReaderWriter;
    }

    @Provides(type = Provides.Type.SET)
    ControllerConfiguration jokeControllerConfiguration() {
        return new JokeController();
    }

    @Provides
    JokeService jokeService() {
        return new JokeServiceImpl();
    }

}
