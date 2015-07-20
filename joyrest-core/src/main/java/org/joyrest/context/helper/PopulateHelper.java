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

import java.util.List;
import java.util.Map;

import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;

public final class PopulateHelper {

    public static void populateRouteReaders(Map<Boolean, List<Reader>> readers, InternalRoute route) {
        if (nonNull(route.getRequestType())) {
            readers.get(TRUE).stream().distinct()
                .filter(transformer -> transformer.isReaderCompatible(route))
                .forEach(route::addReader);

            readers.get(FALSE).stream().distinct()
                .filter(reader -> reader.isReaderCompatible(route))
                .forEach(route::addReader);
        }
    }

    public static void populateRouteWriters(Map<Boolean, List<Writer>> writers, InternalRoute route) {
        if (nonNull(route.getResponseType())) {
            writers.get(TRUE).stream().distinct()
                .filter(writer -> writer.isWriterCompatible(route))
                .forEach(route::addWriter);

            writers.get(FALSE).stream().distinct()
                .filter(writer -> writer.isWriterCompatible(route))
                .forEach(route::addWriter);
        }
    }

    public static void populateHandlerWriters(Map<Boolean, List<Writer>> writers, InternalExceptionHandler handler,
                                              boolean condition) {
        if (condition) {
            writers.get(TRUE).stream().distinct()
                .forEach(handler::addWriter);

            writers.get(FALSE).stream().distinct()
                .filter(writer -> writer.isClassCompatible(handler.getResponseType().getType()))
                .forEach(handler::addWriter);
        }
    }
}
