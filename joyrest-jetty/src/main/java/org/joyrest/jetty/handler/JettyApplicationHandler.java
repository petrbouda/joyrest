package org.joyrest.jetty.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.joyrest.context.ApplicationContext;
import org.joyrest.jetty.model.JettyRequestWrapper;
import org.joyrest.jetty.model.JettyResponseWrapper;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

public class JettyApplicationHandler extends AbstractHandler {

    /* Class for processing an incoming model and generated response */
    private final RequestProcessor processor;

    public JettyApplicationHandler(ApplicationContext applicationContext) {
        this(new RequestProcessorImpl(applicationContext));
    }

    public JettyApplicationHandler(RequestProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
            processor.process(new JettyRequestWrapper(request), new JettyResponseWrapper(response));
            baseRequest.setHandled(true);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}