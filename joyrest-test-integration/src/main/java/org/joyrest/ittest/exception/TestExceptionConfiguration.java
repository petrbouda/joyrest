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
package org.joyrest.ittest.exception;

import java.util.Date;

import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.ittest.entity.Contact;
import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.ittest.exception.exceptions.ContactException;
import org.joyrest.ittest.exception.exceptions.FirstException;
import org.joyrest.ittest.exception.exceptions.ThirdException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class TestExceptionConfiguration extends TypedExceptionConfiguration {

    @Override
    protected void configure() {
        FeedEntry entry = new FeedEntry();
        entry.setLink("http://localhost:8080");
        entry.setPublishDate(new Date());
        entry.setTitle("My Feed Title");
        entry.setDescription("My Feed Description");

        handle(FirstException.class, (req, resp, ex) -> {
            resp.status(HttpStatus.PRECONDITION_FAILED);
            resp.header(HeaderName.of("x-special-header"), "SPECIAL");
        });

        handle(ThirdException.class, (req, resp, ex) -> {
            resp.status(HttpStatus.BAD_REQUEST);
            resp.header(HeaderName.of("x-special-header"), "SPECIAL");
            resp.entity(entry);
        }, Resp(FeedEntry.class));

        handle(ContactException.class, (req, resp, ex) -> {
            resp.status(HttpStatus.BAD_REQUEST);
            resp.header(HeaderName.of("x-special-header"), "SPECIAL");
            resp.entity(new Contact("Petr", "Bouda"));
        }, Resp(Contact.class));
    }
}
