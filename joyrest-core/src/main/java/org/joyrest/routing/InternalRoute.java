/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.extractor.param.IntegerPath;
import org.joyrest.extractor.param.LongPath;
import org.joyrest.extractor.param.PathType;
import org.joyrest.extractor.param.StringPath;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.utils.PathUtils;

/**
 * Container for all information about one route {@link InternalRoute}
 *
 * @author pbouda
 */
public class InternalRoute implements Route {

	private final static JoyLogger logger = new JoyLogger(InternalRoute.class);

	private final static String SLASH = "/";

	/* This character determines whether the given part is PARAM or not */
	private static final String START_PARAM = "{";
	private static final int START_PARAM_LENGTH = START_PARAM.length();

	private static final String END_PARAM = "}";
	private static final int END_PARAM_LENGTH = END_PARAM.length();

	private static final String NAME_TYPE_DELIMITER = ":";

	/* All path param types which are available for a creation of route */
	private final static Map<String, PathType<?>> PATH_TYPES;

	static {
		PATH_TYPES = new HashMap<>();
		PATH_TYPES.put(StringPath.NAME, StringPath.INSTANCE);
		PATH_TYPES.put(IntegerPath.NAME, IntegerPath.INSTANCE);
		PATH_TYPES.put(LongPath.NAME, LongPath.INSTANCE);
	}

	private final HttpMethod httpMethod;

	/* List of the all path's parts which contains this route */
	private final List<RoutePart<?>> routeParts;

	/* Map of the all path params which contains this route */
	private final Map<String, RoutePart<?>> pathParams = new HashMap<>();

	/* Parser what is responsible for getting params from the given path */
	private final ParamParser PARAM_PARSER = new ParamParser();

	/* All Readers and Writers added to the application dedicated for this route */
	private Map<MediaType, Reader> readers = new HashMap<>();
	private Map<MediaType, Writer> writers = new HashMap<>();

	/* It is not FINAL because of adding a global path */
	private String path;

	/* Flag that indicates having a resource path in the list of the RouteParts */
	private boolean hasGlobalPath = false;

	/* Must match with ContentType header in the client's model */
	private List<MediaType> consumes = Collections.singletonList(MediaType.WILDCARD);

	/* Final MediaType of the Response is determined by the Accept header in the client's model */
	private List<MediaType> produces = Collections.singletonList(MediaType.WILDCARD);

	/* Collection of interceptors which will be applied with execution of this route */
	private List<Aspect> aspects = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	private RouteAction action;

	private Type<?> requestType;
	private Type<?> responseType;

	public <REQ, RESP> InternalRoute(String path, HttpMethod httpMethod, RouteAction<REQ, RESP> action,
			Type<REQ> requestClazz, Type<RESP> responseClazz) {
		this.path = path;
		this.httpMethod = httpMethod;
		this.action = action;
		this.requestType = requestClazz;
		this.responseType = responseClazz;
		this.routeParts = createRouteParts(path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route consumes(MediaType... consumes) {
		this.consumes = Arrays.asList(consumes);
		return this;
	}

	public List<MediaType> getConsumes() {
		return unmodifiableList(consumes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route produces(MediaType... produces) {
		this.produces = Arrays.asList(produces);
		return this;
	}

	public List<MediaType> getProduces() {
		return unmodifiableList(produces);
	}

	private List<RoutePart<?>> createRouteParts(String path) {
		List<String> parts = PathUtils.createPathParts(path);
		return parts.stream()
			.peek(PARAM_PARSER)
			.map(this::mapStringPartToRoutePart)
			.collect(toList());
	}

	private RoutePart<?> mapStringPartToRoutePart(String part) {
		if (isPathParam(part))
			return pathParams.get(getPathParamName(part));

		return new RoutePart<>(RoutePart.Type.PATH, part, StringPath.INSTANCE);
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

	public void addGlobalPath(List<RoutePart<String>> parts) {
		if (!hasGlobalPath) {
			routeParts.addAll(0, parts);
			path = addGlobalPathToPath(parts);
			hasGlobalPath = true;
		} else {
			logger.warn(() -> "A global path has been already set.");
		}
	}

	private String addGlobalPathToPath(List<RoutePart<String>> parts) {
		String basePath = parts.stream().map(RoutePart::getValue).collect(joining(SLASH, SLASH, ""));
		return SLASH.contains(path) ? basePath : basePath + path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route aspect(Aspect... aspect) {
		requireNonNull(aspect, "An added aspect cannot be null.");
		aspects.addAll(Arrays.asList(aspect));
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

	public List<Aspect> getAspects() {
		return unmodifiableList(aspects);
	}

	public Optional<Reader> getReader(MediaType mediaType) {
		return Optional.ofNullable(readers.get(mediaType));
	}

	public Map<MediaType, Reader> getReaders() {
		return readers;
	}

	public Map<MediaType, Writer> getWriters() {
		return writers;
	}

	public void setReaders(Map<MediaType, Reader> readers) {
		requireNonNull(readers);
		this.readers = readers;
	}

	public void addReader(Reader reader) {
		requireNonNull(reader);
		this.readers.put(reader.getMediaType(), reader);
	}

	public Optional<Writer> getWriter(MediaType mediaType) {
		return Optional.ofNullable(writers.get(mediaType));
	}

	public void setWriters(Map<MediaType, Writer> writers) {
		requireNonNull(writers);
		this.writers = writers;
	}

	public void addWriter(Writer writer) {
		requireNonNull(writer);
		this.writers.put(writer.getMediaType(), writer);
	}

	private static boolean isPathParam(String part) {
		return part.startsWith(START_PARAM) && part.endsWith(END_PARAM);
	}

	private static String getPathParamName(String part) {
		String param = getPathParam(part);

		/* Split a name and a type of the param */
		return param.split(NAME_TYPE_DELIMITER)[0];
	}

	private static String getPathParam(String part) {
		if (part.startsWith(START_PARAM) && part.endsWith(END_PARAM))
			return part.substring(START_PARAM_LENGTH, part.length() - END_PARAM_LENGTH);

		throw new InvalidConfigurationException(String.format(
				"Invalid path param configuration '%s'", part));
	}

	/**
	 * Contains a logic for parsing data from the part of the given route
	 *
	 * @author pbouda
	 */
	private final class ParamParser implements Consumer<String> {

		@Override
		public void accept(String part) {
			if (isPathParam(part)) {
				/* Split a name and a type of the param */
				String[] split = getPathParam(part).split(NAME_TYPE_DELIMITER);

				String paramName = split[0];
				PathType<?> pathType = null;

				/* Duplication param-name in one route */
				if (pathParams.containsKey(paramName))
					throw new InvalidConfigurationException(String.format(
							"Route '%s' contains more path params with the same name '%s'.", path, paramName));

				/* param without a definition of a type - String default */
				if (split.length == 1)
					pathType = StringPath.INSTANCE;

				/* param with a definition of a type */
				else if (split.length == 2) {
					String paramType = split[1];
					Optional<PathType<?>> optPathType = Optional.ofNullable(PATH_TYPES.get(paramType));
					pathType = optPathType.orElseThrow(() ->
						new InvalidConfigurationException(String.format(
								"Missing a path type for param '%s' and type '%s' in the route '%s'.",
								paramName, paramType, path)));
				}

				/* There is no valid type of the param */
				else if (split.length == 0 || split.length > 2)
					throw new InvalidConfigurationException(String.format(
							"Invalid format of the path param '%s' in the route '%s'.", part, path));

				pathParams.put(paramName, new RoutePart<>(RoutePart.Type.PARAM, paramName, pathType));
			}
		}
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
