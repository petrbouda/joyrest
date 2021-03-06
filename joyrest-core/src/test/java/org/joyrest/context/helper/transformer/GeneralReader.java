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
package org.joyrest.context.helper.transformer;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.Reader;

public class GeneralReader implements Reader {

    @Override
    public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
        return null;
    }

    @Override
    public boolean isReaderCompatible(InternalRoute route) {
        return route.getRequestType().getType() == String.class;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.of("reader/GENERAL");
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
