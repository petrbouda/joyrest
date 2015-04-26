package org.joyrest.grizzly.handler;

import static org.joyrest.utils.RequestResponseUtils.createPath;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.joyrest.context.ApplicationContext;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.LambdaRequest;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

/**
 * Class that extends {@link HttpHandler} because of handling request transfer them into {@link InternalRequest} (internal representation of
 * an incoming request in JoyREST Framework)
 *
 * @author pbouda
 */
public class GrizzlyApplicationHandler extends HttpHandler {

	/* Class for processing an incoming request and generated response */
	private final RequestProcessor processor;

	public GrizzlyApplicationHandler(ApplicationContext applicationContext) {
		this(new RequestProcessorImpl(applicationContext));
	}

	public GrizzlyApplicationHandler(RequestProcessor processor) {
		super(GrizzlyApplicationHandler.class.getName());
		this.processor = processor;
	}

	@Override
	public void service(org.glassfish.grizzly.http.server.Request request,
			org.glassfish.grizzly.http.server.Response response) throws Exception {
		processor.process(createRequest(request), createResponse(response));
	}

	private LambdaRequest<Object> createRequest(org.glassfish.grizzly.http.server.Request request) {
		LambdaRequest<Object> joyRequest = new LambdaRequest<>(request::getHeader, request::getParameterValues);
		joyRequest.setPath(createPath(request.getRequestURI(), request.getContextPath()));
		joyRequest.setMethod(HttpMethod.of(request.getMethod().getMethodString()));
		joyRequest.setInputStream(request.getInputStream());
		joyRequest.setHeaderNames(request.getHeaderNames());
		joyRequest.setQueryParamNames(request.getParameterNames());
		return joyRequest;
	}

	private LambdaResponse<Object> createResponse(org.glassfish.grizzly.http.server.Response response) throws IOException {
		LambdaResponse<Object> joyResponse = new LambdaResponse<>(response::addHeader,
				status -> response.setStatus(status.code()));
		joyResponse.setOutputStream(response.getOutputStream());
		return joyResponse;
	}

}
