package org.joyrest.jetty.handler;

import static java.util.Collections.list;
import static org.joyrest.utils.RequestResponseUtils.createPath;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.joyrest.context.ApplicationContext;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.LambdaRequest;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

public class JettyApplicationHandler extends AbstractHandler {

	/* Class for processing an incoming request and generated response */
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
			processor.process(createRequest(request), createResponse(response));
			baseRequest.setHandled(true);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private LambdaResponse<Object> createResponse(HttpServletResponse response) throws IOException {
		LambdaResponse<Object> joyResponse = new LambdaResponse<>(response::addHeader,
				status -> response.setStatus(status.code()));
		joyResponse.setOutputStream(response.getOutputStream());
		return joyResponse;
	}

	private LambdaRequest<Object> createRequest(HttpServletRequest req) throws IOException {
		LambdaRequest<Object> joyRequest = new LambdaRequest<>(req::getHeader, req::getParameterValues);
		joyRequest.setPath(createPath(req.getRequestURI(), req.getContextPath(), req.getServletPath()));
		joyRequest.setMethod(HttpMethod.of(req.getMethod()));
		joyRequest.setInputStream(req.getInputStream());
		joyRequest.setQueryParamNames(list(req.getParameterNames()));
		joyRequest.setHeaderNames(list(req.getHeaderNames()));
		return joyRequest;
	}

}