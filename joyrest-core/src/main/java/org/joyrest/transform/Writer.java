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
package org.joyrest.transform;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;

/**
 * Provides the methods for writing outcoming entity to outputstream
 *
 * @see AbstractReaderWriter
 * @author pbouda
 */
public interface Writer extends Transformer {

    /**
     * Writes an entity to the outputstream
     *
     * @param response an outcoming response
     * @param request an incoming provider
     */
    void writeTo(InternalResponse<?> response, InternalRequest<?> request);

    /**
     * Figures out whether is writer compatible for the given route
     *
     * @param route compared route
     * @return returns {@code true} if the writer is compatible with the given route
     */
    boolean isWriterCompatible(InternalRoute route);

}
