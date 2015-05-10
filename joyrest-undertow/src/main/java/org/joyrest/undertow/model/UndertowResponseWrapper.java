package org.joyrest.undertow.model;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class UndertowResponseWrapper extends InternalResponse<Object> {

	private final HttpServerExchange response;

	private final Map<HeaderName, String> headers = new HashMap<>();

	private HttpStatus status;

	public UndertowResponseWrapper(HttpServerExchange response) {
		this.response = response;
	}

	@Override
	public OutputStream getOutputStream() {
		return response.getOutputStream();
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		return headers;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public org.joyrest.model.response.Response<Object> header(HeaderName name, String value) {
		response.getResponseHeaders().put(new HttpString(name.getValue()), value);
		this.headers.put(name, value);
		return this;
	}

	@Override
	public org.joyrest.model.response.Response<Object> status(HttpStatus status) {
		response.setResponseCode(status.code());
		this.status = status;
		return this;
	}
}
