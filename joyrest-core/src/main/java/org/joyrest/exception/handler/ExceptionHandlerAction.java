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
package org.joyrest.exception.handler;

import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

/**
 * Class that represents action which is execute when the given exception is thrown.
 *
 * @param <RESP> represents type of the response object
 * @param <E> represents type of the handled exception
 *
 * @see ExceptionHandler
 * @see InternalExceptionHandler
 *
 * @author pbouda
 */
@FunctionalInterface
public interface ExceptionHandlerAction<RESP, E extends Exception> {

    /**
     * Method represents the action that is execute when the given exception is thrown.
     *
     * @param request provider object injected in the handler action
     * @param response response object injected in the handler action
     * @param exception object of the thrown exception
     */
    void perform(Request<?> request, Response<RESP> response, E exception);

}
