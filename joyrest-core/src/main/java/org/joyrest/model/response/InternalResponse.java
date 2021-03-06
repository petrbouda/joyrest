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
package org.joyrest.model.response;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public abstract class InternalResponse<E> implements Response<E> {

    private E entity;

    private boolean entityWritten;

    public abstract OutputStream getOutputStream();

    public abstract Map<HeaderName, String> getHeaders();

    public abstract HttpStatus getStatus();

    @Override
    public Response<E> header(String name, String value) {
        return header(HeaderName.of(name), value);
    }

    @Override
    public InternalResponse<E> entity(E entity) {
        this.entity = entity;
        return this;
    }

    public Optional<E> getEntity() {
        return Optional.ofNullable(entity);
    }

    public boolean isEntityWritten() {
        return entityWritten;
    }

    public void setEntityWritten(boolean entityWritten) {
        this.entityWritten = entityWritten;
    }
}
