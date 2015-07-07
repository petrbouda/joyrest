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
package org.joyrest.context;

import java.util.List;
import java.util.Map;

import org.joyrest.context.configurer.Configurer;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;

/**
 * The heart of the JoyREST Framework that contains all needed configurer for successful running the framework.
 * It doesn't do any work just store the information about the framework and application that is built on that.
 *
 * @see ApplicationContextImpl
 * @see Configurer
 * @author pbouda
 */
public interface ApplicationContext {

    /**
     * Returns all instances of {@link InternalRoute} that were added into an application
     *
     * @return collection of {@link InternalRoute} configured into an application
     */
    List<InternalRoute> getRoutes();

    /**
     * Returns all instances of exception handlers that were added into an application
     *
     * @return map of exception handlers configured into an application
     */
    Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionHandlers();

}
