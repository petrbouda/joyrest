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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

public class XmlReaderWriter extends AbstractReaderWriter {

    private static final MediaType SUPPORTED_MEDIA_TYPE = MediaType.XML;

    private final Marshaller marshaller;

    private final Unmarshaller unmarshaller;

    public XmlReaderWriter(Class<?>... clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            this.marshaller = context.createMarshaller();
            this.unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new InvalidConfigurationException("Unable to create XML Reader and Writer.", e);
        }
    }

    public XmlReaderWriter(String contextPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(contextPath);
            this.marshaller = context.createMarshaller();
            this.unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new InvalidConfigurationException("Unable to create XML Reader and Writer.", e);
        }
    }

    public XmlReaderWriter(JAXBContext context) {
        try {
            this.marshaller = context.createMarshaller();
            this.unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new InvalidConfigurationException("Unable to create XML Reader and Writer.", e);
        }
    }

    public XmlReaderWriter(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readFrom(InternalRequest<Object> request, Type<T> clazz) {
        try {
            return (T) unmarshaller.unmarshal(request.getInputStream());
        } catch (JAXBException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Enable to unmarshall incoming body.");
        }
    }

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        try {
            marshaller.marshal(response.getEntity().get(), response.getOutputStream());
        } catch (JAXBException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Enable to marshall outgoing body.");
        }
    }

    @Override
    public MediaType getMediaType() {
        return SUPPORTED_MEDIA_TYPE;
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return true;
    }
}
