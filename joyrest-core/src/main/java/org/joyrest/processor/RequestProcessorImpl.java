package org.joyrest.processor;

import com.codepoetics.protonpack.StreamUtils;
import org.joyrest.aspect.AspectChain;
import org.joyrest.aspect.AspectChainImpl;
import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.processor.ExceptionProcessor;
import org.joyrest.exception.processor.ExceptionProcessorImpl;
import org.joyrest.exception.type.RestException;
import org.joyrest.extractor.PathParamExtractor;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.http.PathParam;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;
import org.joyrest.routing.strategy.CachedRouteResolver;
import org.joyrest.routing.strategy.DefaultRouteResolver;
import org.joyrest.routing.strategy.RouteResolver;
import org.joyrest.transform.Writer;
import org.joyrest.transform.WriterRegistrar;
import org.joyrest.utils.SerializationUtils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.joyrest.exception.type.RestException.notFoundSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;

/**
 * {@inheritDoc}
 * <p/>
 * The processor gets {@link ApplicationContext} with all information which
 * can be provided by framework regarding configured routes.
 *
 * @author pbouda
 */
public class RequestProcessorImpl implements RequestProcessor {

	/* Class is able to extract path params from incoming path according an info from route */
	private final PathParamExtractor pathParamExtractor = new PathParamExtractor();

	/* Classes for route resolving - find the correct route according to the incoming request */
	private final RouteResolver defaultRouteResolver;
	private final RouteResolver cachedRouteResolver;

	/* Classes for deserializing entities */
	private final DefaultMultiMap<MediaType, WriterRegistrar> writers;

	/* Class for a ecxeption processing */
	private final ExceptionProcessor exceptionProcessor;

	/* Class contains all information about a running application */
	private final ApplicationContext context;

	public RequestProcessorImpl(ApplicationContext context) {
		this.context = context;
		this.writers = context.getWriters();
		this.defaultRouteResolver = new DefaultRouteResolver(context);
		this.cachedRouteResolver = new CachedRouteResolver(context);
		this.exceptionProcessor = new ExceptionProcessorImpl(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response process(final InternalRequest request, final InternalResponse response) throws Exception {
		try {
			return processRequest(request, response);
		} catch (RestException re) {
			return processException(request, re.getResponse());
		} catch (Exception t) {
			this.exceptionProcessor.process(t, request, response);
			return processException(request, response);
		}
	}

	private Response processException(final InternalRequest request, final Response response) {
		writeEntity(request, response);
		return response;
	}

	private Response processRequest(final InternalRequest request, final InternalResponse response) {
		final Route route = cachedRouteResolver.resolveRoute(request)
			.chainEmpty(defaultRouteResolver.resolveRoute(request))
			.orElseThrow(notFoundSupplier());

		request.setPathParams(resolvePathParams(route, request));

		AspectChain chain = new AspectChainImpl(route);
		return chain.proceed(request, response);
	}

	private void writeEntity(final Request request, final Response response) {
		if (response.getEntity().isPresent()) {
			MediaType accept = request.getHeader(ACCEPT).map(MediaType::of).get();
			Class<?> responseClass = response.getEntity().get().getClass();
			Writer writer = SerializationUtils.selectWriter(writers, responseClass, accept);
			writer.writeTo(response);
		}
	}

	private Map<String, PathParam> resolvePathParams(final Route route, final Request request) {
		return StreamUtils
			.zip(route.getRouteParts().stream(), request.getPathParts().stream(), pathParamExtractor)
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(PathParam::getName, Function.identity()));
	}
}
