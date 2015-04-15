package org.joyrest.model.request;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.PathParam;

public interface Request<E> {

	Map<HeaderName, String> getHeaders();

	Map<String, String[]> getQueryParams();

	Map<String, PathParam> getPathParams();

	HttpMethod getMethod();

	String getPath();

	List<String> getPathParts();

	String getPathParam(String name);

	Optional<String[]> getQueryParams(String name);

	Optional<String> getHeader(HeaderName name);

	E getEntity();

}