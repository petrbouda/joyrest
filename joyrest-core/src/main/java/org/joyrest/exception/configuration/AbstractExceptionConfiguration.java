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
package org.joyrest.exception.configuration;

import java.util.HashSet;
import java.util.Set;

import org.joyrest.exception.handler.ExceptionHandlerAction;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.entity.Type;

/**
 * Helper class for definition {@link ExceptionConfiguration} objects that are used to configure handlers for registering
 * some actions bound to a type of exceptions.
 *
 * @see TypedExceptionConfiguration
 * @author pbouda
 */
public abstract class AbstractExceptionConfiguration implements ExceptionConfiguration {

    /* Set of handlers which are configured in an inherited class */
    private final Set<InternalExceptionHandler> handlers = new HashSet<>();

    /* ExceptionConfiguration's initialization should be executed only once */
    private boolean isInitialized = false;

    /**
     * Method in which are registered handlers for exceptions.
     */
    protected abstract void configure();

    @Override
    public final void initialize() {
        if (!this.isInitialized) {
            configure();

            this.isInitialized = true;
        }
    }

    @Override
    public Set<InternalExceptionHandler> getExceptionHandlers() {
        return handlers;
    }

    /**
     * Method registers a new handler to some type of exception.
     *
     * @param clazz type of the exception to which is handler registered
     * @param action action that is performed when the proper exception is thrown
     * @param ex thrown exception
     * @param <T> type of the thrown exception
     * @param <RESP> type of the returned object
     * @return registered exception handler
     */
    public <T extends Exception, RESP> InternalExceptionHandler putHandler(Class<T> clazz,
                                                                           ExceptionHandlerAction<RESP, T> action,
                                                                           Type<RESP> ex) {
        final InternalExceptionHandler handler = new InternalExceptionHandler(clazz, action, ex);
        handlers.add(handler);
        return handler;
    }
}
