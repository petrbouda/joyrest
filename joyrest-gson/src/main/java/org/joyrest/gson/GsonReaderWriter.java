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
package org.joyrest.gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

import com.google.gson.Gson;

public class GsonReaderWriter extends AbstractReaderWriter {

    private static final Gson GSON = new Gson();

    private static final MediaType SUPPORTED_MEDIA_TYPE = MediaType.JSON;

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        try (PrintStream ps = new PrintStream(response.getOutputStream())) {
            GSON.toJson(response.getEntity().get(), ps);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readFrom(InternalRequest<Object> request, Type<T> type) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        return GSON.fromJson(bufferedReader, (Class<T>) type.getType());
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return true;
    }

    @Override
    public MediaType getMediaType() {
        return SUPPORTED_MEDIA_TYPE;
    }
}
