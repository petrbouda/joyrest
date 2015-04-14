package org.joyrest.model.request;

import org.joyrest.model.http.*;

import java.io.InputStream;
import java.util.*;

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