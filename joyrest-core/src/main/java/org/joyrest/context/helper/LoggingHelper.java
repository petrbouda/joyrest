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
package org.joyrest.context.helper;

import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.logging.JoyLogger;
import org.joyrest.routing.InternalRoute;

public final class LoggingHelper {

    private static final JoyLogger log = new JoyLogger(LoggingHelper.class);

    public static void logExceptionHandler(InternalExceptionHandler handler) {
        log.info(() -> String.format("Exception Handler instantiated: EXCEPTION-CLASS[%s], RESP-CLASS[%s]",
            handler.getExceptionClass(), handler.getResponseType()));

        handler.getWriters()
            .forEach((type, writer) ->
                log.debug(() -> String.format("Writer [%s, %s] added to the Exception Handler [EXCEPTION-CLASS[%s]]",
                    writer.getClass().getSimpleName(), writer.getMediaType(), handler.getExceptionClass())));
    }

    public static void logRoute(InternalRoute route) {
        log.info(() -> String.format(
            "Route instantiated: METHOD[%s], PATH[%s], CONSUMES[%s], PRODUCES[%s], REQ-CLASS[%s], RESP-CLASS[%s]",
            route.getHttpMethod(), route.getPath(), route.getConsumes(), route.getProduces(),
            route.getRequestType(), route.getResponseType()));

        route.getInterceptors()
            .forEach(aspect ->
                log.debug(() -> String.format("Aspect [%s] added to the Route [METHOD[%s], PATH[%s]]",
                    aspect.getClass().getSimpleName(), route.getHttpMethod(), route.getPath())));

        route.getReaders()
            .forEach((type, reader) ->
                log.debug(() -> String.format("Reader [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
                    reader.getClass().getSimpleName(), reader.getMediaType(), route.getHttpMethod(),
                    route.getPath())));

        route.getWriters()
            .forEach((type, writer) ->
                log.debug(() -> String.format("Writer [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
                    writer.getClass().getSimpleName(), writer.getMediaType(), route.getHttpMethod(),
                    route.getPath())));
    }
}
