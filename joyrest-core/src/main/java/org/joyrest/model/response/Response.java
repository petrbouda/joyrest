package org.joyrest.model.response;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public interface Response {

	Optional<Object> getEntity();

	OutputStream getOutputStream();

	Map<HeaderName, String> getHeaders();

	HttpStatus getStatus();

	Response header(HeaderName name, String value);

	Response status(HttpStatus status);

	Response entity(Object entity);
}