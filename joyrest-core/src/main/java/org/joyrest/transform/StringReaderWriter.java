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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Optional;

import org.joyrest.logging.JoyLogger;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;

/**
 * Class is able to read and write entity which corresponds to string
 *
 * @author pbouda
 */
public class StringReaderWriter extends AbstractReaderWriter {

    private final static JoyLogger logger = JoyLogger.of(StringReaderWriter.class);

    private final MediaType supportedMediaType = MediaType.PLAIN_TEXT;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
        Charset charset = Optional.of(request.getContentType())
            .flatMap(mediaType -> mediaType.getParam("charset"))
            .map(Charset::forName)
            .orElse(DEFAULT_CHARSET);

        StringBuilder builder = new BufferedReader(
            new InputStreamReader(request.getInputStream(), charset)).lines()
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        return (T) builder.toString();
    }

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        try {
            if (!response.getEntity().isPresent()) {
                logger.warn(() -> "No entity in the response to write to an output stream.");
                return;
            }

            Charset charset = request.getHeader(HeaderName.ACCEPT_CHARSET)
                .map(Charset::forName)
                .orElse(DEFAULT_CHARSET);

            java.io.Writer writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), charset));
            writer.append((CharSequence) response.getEntity().get());
            writer.flush();
        } catch (UnsupportedEncodingException e) {
            throw internalServerErrorSupplier("Unsupported Encoding Exception").get();
        } catch (IOException e) {
            throw internalServerErrorSupplier("IO Exception occurred during writing from String").get();
        }
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return true;
    }

    @Override
    public MediaType getMediaType() {
        return supportedMediaType;
    }
}
