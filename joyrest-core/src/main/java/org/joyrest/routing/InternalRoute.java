/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;
import org.joyrest.routing.security.Role;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.utils.CollectionUtils;
import static org.joyrest.model.http.MediaType.WILDCARD;
import static org.joyrest.utils.CollectionUtils.nonEmpty;
import static org.joyrest.utils.PathUtils.createPathParts;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Container for all information about one route {@link InternalRoute}
 *
 * @author pbouda
 */
public class InternalRoute implements Route {

    private final static JoyLogger logger = new JoyLogger(InternalRoute.class);

    private final static String SLASH = "/";

    private final HttpMethod httpMethod;

    /* List of the all path's parts which contains this route */
    private final List<RoutePart<?>> routeParts;

    /* Map of the all path params which contains this route */
    private final Map<String, RoutePart<?>> pathParams = new HashMap<>();

    /* All Readers and Writers added to the application dedicated for this route */
    private Map<MediaType, Reader> readers = new HashMap<>();
    private Map<MediaType, Writer> writers = new HashMap<>();

    /* It is not FINAL because of adding a controller path */
    private String path;

    /* Flag that indicates having a resource path in the list of the RouteParts */
    private boolean hasControllerPath = false;

    /* Must match with ContentType header in the client's model */
    private List<MediaType> consumes = singletonList(WILDCARD);

    /* Final MediaType of the Response is determined by the Accept header in the client's model */
    private List<MediaType> produces = singletonList(WILDCARD);

    /* Collection of interceptors which will be applied with execution of this route */
    private List<Interceptor> interceptors = new ArrayList<>();

	/* Collection of roles which protect this route */
	private List<String> roles = new ArrayList<>();

    @SuppressWarnings("rawtypes")
    private RouteAction action;

    private Type<?> requestType;
    private Type<?> responseType;

    public InternalRoute(String path, HttpMethod httpMethod,
                         RouteAction action, Type<?> requestClazz, Type<?> responseClazz) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.action = action;
        this.requestType = requestClazz;
        this.responseType = responseClazz;
        this.routeParts = createRouteParts(path);
    }

    @Override
    public Route consumes(MediaType... consumes) {
        this.consumes = asList(consumes);
        return this;
    }

    public List<MediaType> getConsumes() {
        return unmodifiableList(consumes);
    }

    @Override
    public Route produces(MediaType... produces) {
        this.produces = asList(produces);
        return this;
    }

    public List<MediaType> getProduces() {
        return unmodifiableList(produces);
    }

    private List<RoutePart<?>> createRouteParts(String path) {
        return createPathParts(path).stream()
            .map(new ParamParser(path))
            .peek(part -> {
                // Save path params to the map
                if (part.getType() == RoutePart.Type.PARAM) {
                    pathParams.put(part.getValue(), part);
                }
            })
            .collect(toList());
    }

    public List<RoutePart<?>> getRouteParts() {
        return isNull(routeParts) ? new ArrayList<>() : unmodifiableList(routeParts);
    }

    public String getPath() {
        return path;
    }

    public Map<String, RoutePart<?>> getPathParams() {
        return unmodifiableMap(pathParams);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void addControllerPath(List<RoutePart<String>> parts) {
        if (!hasControllerPath) {
            routeParts.addAll(0, parts);
            path = addControllerPathToPath(parts);
            hasControllerPath = true;
        } else {
            logger.warn(() -> "A controller path has been already set.");
        }
    }

    private String addControllerPathToPath(List<RoutePart<String>> parts) {
        String basePath = parts.stream()
            .map(RoutePart::getValue)
            .collect(joining(SLASH, SLASH, ""));
        return SLASH.contains(path) ? basePath : basePath + path;
    }

    @Override
    public Route interceptor(Interceptor... interceptor) {
        interceptors.addAll(asList(interceptor));
        return this;
    }

    public Type getRequestType() {
        return requestType;
    }

    public Type<?> getResponseType() {
        return responseType;
    }

    public boolean hasRequestBody() {
        return Objects.nonNull(requestType);
    }

    @SuppressWarnings("unchecked")
    public InternalResponse<Object> execute(InternalRequest<Object> request, InternalResponse<Object> response) {
        action.perform(ImmutableRequest.of(request), response);
        return response;
    }

    public List<Interceptor> getInterceptors() {
        return unmodifiableList(interceptors);
    }

    public Optional<Reader> getReader(MediaType mediaType) {
        return Optional.ofNullable(readers.get(mediaType));
    }

    public Map<MediaType, Reader> getReaders() {
        return readers;
    }

    public void setReaders(Map<MediaType, Reader> readers) {
        requireNonNull(readers);
        this.readers = readers;
    }

    public Map<MediaType, Writer> getWriters() {
        return writers;
    }

    public void setWriters(Map<MediaType, Writer> writers) {
        requireNonNull(writers);
        this.writers = writers;
    }

    public void addReader(Reader reader) {
        requireNonNull(reader);
        this.readers.put(reader.getMediaType(), reader);
    }

    public Optional<Writer> getWriter(MediaType mediaType) {
        return Optional.ofNullable(writers.get(mediaType));
    }

    public void addWriter(Writer writer) {
        requireNonNull(writer);
        this.writers.put(writer.getMediaType(), writer);
    }

	@Override
	public Route roles(String... roles) {
		this.roles = asList(roles);
		return this;
	}

	public List<String> getRoles() {
		return unmodifiableList(roles);
	}

    public boolean isSecured() {
        return nonEmpty(roles);
    }

	@Override
    public int hashCode() {
        return Objects.hash(httpMethod, path);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final InternalRoute other = (InternalRoute) obj;
        return Objects.equals(this.httpMethod, other.httpMethod)
            && Objects.equals(this.path, other.path);
    }
}
