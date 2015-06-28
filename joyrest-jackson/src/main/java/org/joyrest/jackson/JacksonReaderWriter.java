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
package org.joyrest.jackson;

import java.io.IOException;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.AbstractReaderWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonReaderWriter extends AbstractReaderWriter {

	private static final ObjectMapper mapper = new ObjectMapper();

	private static final MediaType SUPPORTED_MEDIA_TYPE = MediaType.JSON;

	@Override
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
		try {
			mapper.writeValue(response.getOutputStream(), response.getEntity().get());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during marshalling from JSON.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFrom(InternalRequest<Object> request, Type<T> type) {
		try {
			return mapper.readValue(request.getInputStream(), (Class<T>) type.getType());
		} catch (IOException e) {
			throw new RuntimeException("An error occurred during unmarshalling from JSON.");
		}
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
