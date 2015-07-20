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
package org.joyrest.grizzly.handler;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.model.GrizzlyRequestWrapper;
import org.joyrest.grizzly.model.GrizzlyResponseWrapper;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

public class GrizzlyApplicationHandler extends HttpHandler {

    /* Class for processing an incoming model and generated response */
    private final RequestProcessor processor;

    public GrizzlyApplicationHandler(ApplicationContext applicationContext) {
        this(new RequestProcessorImpl(applicationContext));
    }

    public GrizzlyApplicationHandler(RequestProcessor processor) {
        super(GrizzlyApplicationHandler.class.getName());
        this.processor = processor;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        processor.process(new GrizzlyRequestWrapper(request), new GrizzlyResponseWrapper(response));
    }

}