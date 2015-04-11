package org.joyrest.routing;

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

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Container for all information about one route {@link EntityRoute}
 *
 * @author pbouda
 */
public class EntityRoute<REQ, RESP> implements Route<REQ, RESP> {

	private final static JoyLogger logger = new JoyLogger(EntityRoute.class);

	private final static String SLASH = "/";

	/* All path param types which are available for a creation of route */
	private final static Map<String, PathType<?>> pathTypes;

	/* All Readers added to the application */
	private Map<MediaType, Reader<REQ>> readers = new HashMap<>();
	private Map<MediaType, Writer<RESP>> writers = new HashMap<>();

	static {
		pathTypes = new HashMap<>();
		pathTypes.put(StringPath.NAME, StringPath.INSTANCE);
		pathTypes.put(IntegerPath.NAME, IntegerPath.INSTANCE);
		pathTypes.put(LongPath.NAME, LongPath.INSTANCE);
	}

	private final HttpMethod httpMethod;

	/* It is not FINAL because of adding a global path */
	private String path;

	/* List of the all path's parts which contains this route */
	private final List<RoutePart<?>> routeParts;

	/* Map of the all path params which contains this route */
	private final Map<String, RoutePart<?>> pathParams = new HashMap<>();

	/* Parser what is responsible for getting params from the given path */
	private final ParamParser PARAM_PARSER = new ParamParser();

	/* Flag that indicates having a resource path in the list of the RouteParts */
	private boolean hasGlobalPath = false;

	/* Must match with ContentType header in the client's request  */
	private List<MediaType> consumes = Collections.singletonList(MediaType.WILDCARD);

	/* Final MediaType of the Response is determined by the Accept header in the client's request*/
	private List<MediaType> produces = Collections.singletonList(MediaType.WILDCARD);

	/* Collection of interceptors which will be applied with execution of this route */
	private List<Aspect<REQ, RESP>> aspects = new ArrayList<>();

	private BiConsumer<Request<REQ>, Response<RESP>> action;

	private Type<REQ> requestType;
	private Type<RESP> responseType;

	public EntityRoute(String path, HttpMethod httpMethod, BiConsumer<Request<REQ>, Response<RESP>> action,
		Type<REQ> requestClazz, Type<RESP> responseClazz) {
		this.path = path;
		this.httpMethod = httpMethod;
		this.action = action;
		this.requestType = requestClazz;
		this.responseType = responseClazz;
		this.routeParts = createRouteParts(path);
	}

	public EntityRoute<REQ, RESP> consumes(MediaType... consumes) {
		this.consumes = Arrays.asList(consumes);
		return this;
	}

	@Override
	public List<MediaType> getConsumes() {
		return Collections.unmodifiableList(consumes);
	}

	public EntityRoute<REQ, RESP> produces(MediaType... produces) {
		this.produces = Arrays.asList(produces);
		return this;
	}

	@Override
	public List<MediaType> getProduces() {
		return Collections.unmodifiableList(produces);
	}

	private List<RoutePart<?>> createRouteParts(String path) {
		List<String> parts = PathUtils.createPathParts(path);
		return parts.stream().peek(PARAM_PARSER)
			.map(this::mapStringPartToRoutePart)
			.collect(Collectors.toList());
	}

	private RoutePart<?> mapStringPartToRoutePart(String part) {
		if (pathParams.containsKey(part)) {
			return pathParams.get(part);
		}

		return new RoutePart<>(RoutePart.Type.PATH, part, StringPath.INSTANCE);
	}

	@Override
	public List<RoutePart<?>> getRouteParts() {
		return routeParts == null ? new ArrayList<>() : Collections.unmodifiableList(routeParts);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Map<String, RoutePart<?>> getPathParams() {
		return Collections.unmodifiableMap(pathParams);
	}

	@Override
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

	@SafeVarargs
	public final EntityRoute<REQ, RESP> aspect(Aspect<REQ, RESP>... aspect) {
		requireNonNull(aspect, "An added aspect cannot be null.");
		aspects.addAll(Arrays.asList(aspect));
		return this;
	}

	@Override
	public Type<REQ> getRequestType() {
		return requestType;
	}

	@Override
	public Type<RESP> getResponseType() {
		return responseType;
	}

	public boolean hasRequestBody() {
		return Objects.nonNull(requestType);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param request
	 */

	@Override
	public InternalResponse<RESP> execute(InternalRequest<REQ> request, InternalResponse<RESP> response) {
		action.accept(ImmutableRequest.of(request), response);
		return response;
	}

	@Override
	public List<Aspect<REQ, RESP>> getAspects() {
		return Collections.unmodifiableList(aspects);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Reader<REQ>> getReader(MediaType mediaType) {
		return Optional.ofNullable(readers.get(mediaType));
	}

	public void setReaders(Map<MediaType, Reader<REQ>> readers) {
		requireNonNull(readers);
		this.readers = readers;
	}

	public void addReader(Reader<REQ> reader) {
		requireNonNull(reader);
		this.readers.put(reader.getMediaType(), reader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Writer<RESP>> getWriter(MediaType mediaType) {
		return Optional.ofNullable(writers.get(mediaType));
	}

	public void setWriters(Map<MediaType, Writer<RESP>> writers) {
		requireNonNull(writers);
		this.writers = writers;
	}

	public void addWriter(Writer<RESP> writer) {
		requireNonNull(writer);
		this.writers.put(writer.getMediaType(), writer);
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
		final EntityRoute<REQ, RESP> other = (EntityRoute<REQ, RESP>) obj;
		return Objects.equals(this.httpMethod, other.httpMethod)
			&& Objects.equals(this.path, other.path);
	}

	/**
	 * Contains a logic for parsing data from the part of the given route
	 *
	 * @author pbouda
	 */
	private final class ParamParser implements Consumer<String> {

		/* This character determines whether the given part is PARAM or not */
		private static final String PARAM_CHAR = "$";

		@Override
		public void accept(String part) {
			if (part.startsWith(PARAM_CHAR)) {
				String var = part.replaceFirst(PARAM_CHAR, part);

                /* Split a name and a type of the param  */
				String[] split = var.split(":");

                /* param without a definition of a type - String default */
				if (split.length == 1) {
					String paramName = split[0];
					pathParams.put(paramName, new RoutePart<>(RoutePart.Type.PARAM, paramName, StringPath.INSTANCE));
				}

                /* param with a definition of a type */
				if (split.length == 2) {
					String paramName = split[0];
					String strParamType = split[1];

					Optional<PathType<?>> optPathType = Optional.ofNullable(pathTypes.get(strParamType));
					PathType<?> pathType = optPathType.orElseThrow(() ->
						new InvalidConfigurationException("Invalid configuration of the route '" + path + "'."));
					pathParams.put(paramName,
						new RoutePart<>(RoutePart.Type.PARAM, paramName, pathType));
				}

                /* There is no valid type of the param */
				if (split.length == 0 || split.length > 2)
					throw new InvalidConfigurationException
						("Invalid configuration of the route '" + path + "'.");
			}
		}
	}
}
