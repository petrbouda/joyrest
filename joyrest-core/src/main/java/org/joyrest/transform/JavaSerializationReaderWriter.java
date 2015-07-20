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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

public class JavaSerializationReaderWriter extends AbstractReaderWriter {

    private final MediaType supportedMediaType = MediaType.SERIALIZATION_JAVA;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
        try {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("An error occurred during unmarshalling from Java Serialization.", e);
        }
    }

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
            out.writeObject(response.getEntity().get());
        } catch (IOException e) {
            throw new RuntimeException("An error occurred during marshalling from Java Serialization.", e);
        }
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return Serializable.class.isAssignableFrom(clazz);
    }

    @Override
    public MediaType getMediaType() {
        return supportedMediaType;
    }
}
