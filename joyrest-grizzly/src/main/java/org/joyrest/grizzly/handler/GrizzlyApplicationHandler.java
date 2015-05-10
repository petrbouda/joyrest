package org.joyrest.grizzly.handler;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.model.GrizzlyRequestWrapper;
import org.joyrest.grizzly.model.GrizzlyResponseWrapper;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

/**
 * Class that extends {@link HttpHandler} because of handling model transfer them into {@link InternalRequest} (internal representation of
 * an incoming model in JoyREST Framework)
 *
 * @author pbouda
 */
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