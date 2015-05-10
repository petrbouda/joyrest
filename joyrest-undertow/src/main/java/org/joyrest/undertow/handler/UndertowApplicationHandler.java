package org.joyrest.undertow.handler;

import static java.util.stream.Collectors.toList;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import java.io.IOException;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.LambdaRequest;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class UndertowApplicationHandler implements HttpHandler {

	/* Class for processing an incoming request and generated response */
	private final RequestProcessor processor;

	public UndertowApplicationHandler(ApplicationContext applicationContext) {
		this(new RequestProcessorImpl(applicationContext));
	}

	public UndertowApplicationHandler(RequestProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		processor.process(createRequest(exchange), createResponse(exchange));
	}

	private LambdaRequest<Object> createRequest(HttpServerExchange exchange) {
		LambdaRequest<Object> joyRequest = new LambdaRequest<>(
				headerName -> exchange.getRequestHeaders().getFirst(headerName),
				paramName -> (String[]) exchange.getQueryParameters().get(paramName).toArray());
		joyRequest.setPath(exchange.getRequestPath());
		joyRequest.setMethod(HttpMethod.of(exchange.getRequestMethod().toString()));
		joyRequest.setInputStream(exchange.getInputStream());
		joyRequest.setHeaderNames(
			exchange.getRequestHeaders().getHeaderNames().stream()
				.map(HttpString::toString).collect(toList()));
		joyRequest.setQueryParamNames(exchange.getQueryParameters().keySet());
		joyRequest.setContentType(MediaType.of(exchange.getRequestHeaders().get(CONTENT_TYPE.getValue()).getFirst()));
		joyRequest.setAccept(MediaType.list(exchange.getRequestHeaders().get(ACCEPT.getValue()).getFirst()));
		return joyRequest;
	}

	private LambdaResponse<Object> createResponse(HttpServerExchange exchange) throws IOException {
		LambdaResponse<Object> resp = new LambdaResponse<>(
				(headerName, headerValue) -> exchange.getResponseHeaders().put(new HttpString(headerName), headerValue),
				status -> exchange.setResponseCode(status.code()));
		resp.setOutputStream(exchange.getOutputStream());
		return resp;
	}
}
