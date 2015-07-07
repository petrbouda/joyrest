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
