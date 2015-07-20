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
package org.joyrest.exception.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

/**
 * Implementation of this interface is able to process thrown exception using .
 *
 * @see ExceptionProcessorImpl
 *
 * @author pbouda
 */
@FunctionalInterface
public interface ExceptionProcessor {

    /**
     * Method processes a handled exception.
     *
     * @param ex injected object of handled exception
     * @param request injected provider object
     * @param response injected response object
     * @param <T> type of the handled exception
     * @throws Exception exception occurred during exception processing in framework
     */
    <T extends Exception> void process(T ex, InternalRequest<Object> request, InternalResponse<Object> response) throws Exception;

}
