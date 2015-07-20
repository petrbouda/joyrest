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
package org.joyrest.ittest.exception.writer;

import java.util.Objects;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Writer;

public class UnknownWriter implements Writer {

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        throw new RuntimeException("Should never be thrown");
    }

    @Override
    public boolean isWriterCompatible(InternalRoute route) {
        return false;
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return Objects.equals(clazz, Object.class);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.HTML;
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
