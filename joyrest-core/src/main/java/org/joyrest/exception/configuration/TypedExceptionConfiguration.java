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
package org.joyrest.exception.configuration;

import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.exception.handler.ExceptionHandlerAction;
import org.joyrest.routing.entity.ResponseCollectionType;
import org.joyrest.routing.entity.ResponseType;

/**
 * Helper class for a registering exception handlers in exception configurer and subsequently configuring in application context.
 *
 * @see AbstractExceptionConfiguration
 * @author pbouda
 */
public abstract class TypedExceptionConfiguration extends AbstractExceptionConfiguration {

    /**
     * Method registers a new handler to some type of exception.
     *
     * @param clazz type of the exception to which is handler registered
     * @param action action that is performed when the proper exception is thrown
     * @param <T> type of the thrown exception
     * @param <RESP> type of the returned object
     * @return registered exception handler
     */
    protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz, ExceptionHandlerAction<RESP, T> action) {
        return putHandler(clazz, action, null);
    }

    /**
     * Method registers a new handler to some type of exception.
     *
     * @param clazz type of the exception to which is handler registered
     * @param action action that is performed when the proper exception is thrown
     * @param <T> type of the thrown exception
     * @param <RESP> type of the returned object
     * @param resp type of the response object - single object
     * @return registered exception handler
     */
    protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz, ExceptionHandlerAction<RESP, T> action,
                                                                        ResponseType<RESP> resp) {
        return putHandler(clazz, action, resp);
    }

    /**
     * Method registers a new handler to some type of exception.
     *
     * @param clazz type of the exception to which is handler registered
     * @param action action that is performed when the proper exception is thrown
     * @param <T> type of the thrown exception
     * @param <RESP> type of the returned object
     * @param resp type of the response object - collection
     * @return registered exception handler
     */
    protected final <T extends Exception, RESP> ExceptionHandler handle(Class<T> clazz, ExceptionHandlerAction<RESP, T> action,
                                                                        ResponseCollectionType<RESP> resp) {
        return putHandler(clazz, action, resp);
    }

}
