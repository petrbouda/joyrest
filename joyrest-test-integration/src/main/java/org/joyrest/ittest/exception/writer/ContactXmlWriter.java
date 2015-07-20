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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.joyrest.ittest.entity.Contact;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Writer;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;

public class ContactXmlWriter implements Writer {

    static final JAXBContext context = initContext();

    private static JAXBContext initContext() {
        try {
            return JAXBContext.newInstance(Contact.class);
        } catch (JAXBException e) {
            throw internalServerErrorSupplier(
                "An error occurred during creating JAXBContext in ContactXmlWriter").get();
        }
    }

    @Override
    public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(response.getEntity().get(), response.getOutputStream());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isWriterCompatible(InternalRoute route) {
        return route.getProduces().contains(getMediaType());
    }

    @Override
    public boolean isClassCompatible(Class<?> clazz) {
        return Objects.equals(clazz, Contact.class);
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.XML;
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
