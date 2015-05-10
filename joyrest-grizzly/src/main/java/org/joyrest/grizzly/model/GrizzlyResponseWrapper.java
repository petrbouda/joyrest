package org.joyrest.grizzly.model;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.Response;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;

public class GrizzlyResponseWrapper extends InternalResponse<Object> {

	private final Response response;

	private final Map<HeaderName, String> headers = new HashMap<>();

	private HttpStatus status;

	public GrizzlyResponseWrapper(Response response) {
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
		response.addHeader(name.getValue(), value);
		this.headers.put(name, value);
		return this;
	}

	@Override
	public org.joyrest.model.response.Response<Object> status(HttpStatus status) {
		response.setStatus(status.code());
		this.status = status;
		return this;
	}
}
