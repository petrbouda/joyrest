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
package org.joyrest.undertow.handler;

import org.joyrest.context.ApplicationContext;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;
import org.joyrest.undertow.model.UndertowRequestWrapper;
import org.joyrest.undertow.model.UndertowResponseWrapper;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class UndertowApplicationHandler implements HttpHandler {

    /* Class for processing an incoming model and generated response */
    private final RequestProcessor processor;

    public UndertowApplicationHandler(ApplicationContext applicationContext) {
        this(new RequestProcessorImpl(applicationContext));
    }

    public UndertowApplicationHandler(RequestProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.startBlocking();
        processor.process(new UndertowRequestWrapper(exchange), new UndertowResponseWrapper(exchange));
    }
}
