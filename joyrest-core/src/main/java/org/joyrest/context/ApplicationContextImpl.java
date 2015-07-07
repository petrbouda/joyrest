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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.Route;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

public class ApplicationContextImpl implements ApplicationContext {

    /* Set of all configured items in this application */
    private List<InternalRoute> routes = new ArrayList<>();

    private Map<Class<? extends Exception>, InternalExceptionHandler> exceptionHandlers = new HashMap<>();

    @Override
    public List<InternalRoute> getRoutes() {
        return unmodifiableList(routes);
    }

    /**
     * Adds a collection of {@link Route} into the application
     *
     * @param routes set of routes defined in application
     */
    public void setRoutes(List<InternalRoute> routes) {
        requireNonNull(routes);
        this.routes = routes;
    }

    @Override
    public Map<Class<? extends Exception>, InternalExceptionHandler> getExceptionHandlers() {
        return unmodifiableMap(exceptionHandlers);
    }

    /**
     * Adds a map of {@link ExceptionHandler} into the application
     *
     * @param handlers configurations which keep given handlers
     */
    public void setExceptionHandlers(Map<Class<? extends Exception>, InternalExceptionHandler> handlers) {
        requireNonNull(handlers);
        this.exceptionHandlers = handlers;
    }
}
