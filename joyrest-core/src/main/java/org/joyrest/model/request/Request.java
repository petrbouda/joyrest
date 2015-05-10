package org.joyrest.model.request;

import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.PathParam;

public interface Request<E> {

	Map<HeaderName, String> getHeaders();

	Optional<String> getHeader(HeaderName name);

	Map<String, String[]> getQueryParams();

	Optional<String[]> getQueryParams(String name);

	Map<String, PathParam> getPathParams();

	String getPathParam(String name);

	HttpMethod getMethod();

	String getPath();

	E getEntity();

}