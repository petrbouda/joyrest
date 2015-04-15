package org.joyrest.processor;

import static org.joyrest.exception.type.RestException.notFoundSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.joyrest.aspect.AspectChain;
import org.joyrest.aspect.AspectChainImpl;
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
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.routing.strategy.CachedRouteResolver;
import org.joyrest.routing.strategy.DefaultRouteResolver;
import org.joyrest.routing.strategy.RouteResolver;

import com.codepoetics.protonpack.StreamUtils;

/**
 * {@inheritDoc}
 * <p/>
 * The processor gets {@link ApplicationContext} with all information which can be provided by framework regarding configured routes.
 *
 * @author pbouda
 */
public class RequestProcessorImpl implements RequestProcessor {

	/* Class is able to extract path params from incoming path according an info from route */
	private final PathParamExtractor pathParamExtractor = new PathParamExtractor();

	/* Classes for route resolving - find the correct route according to the incoming request */
	private final RouteResolver defaultRouteResolver;
	private final RouteResolver cachedRouteResolver;

	/* Class for a ecxeption processing */
	private final ExceptionProcessor exceptionProcessor;

	public RequestProcessorImpl(ApplicationContext context) {
		this.defaultRouteResolver = new DefaultRouteResolver(context);
		this.cachedRouteResolver = new CachedRouteResolver(context);
		this.exceptionProcessor = new ExceptionProcessorImpl(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternalResponse<?> process(final InternalRequest<?> request, final InternalResponse<?> response) throws Exception {
		try {
			return processRequest(request, response);
		} catch (Exception ex) {
			return exceptionProcessor.process(ex, request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private InternalResponse<?> processRequest(final InternalRequest<?> request, final InternalResponse<?> response) {
		final EntityRoute route = cachedRouteResolver.resolveRoute(request)
			.chainEmpty(defaultRouteResolver.resolveRoute(request))
			.orElseThrow(notFoundSupplier());

		request.setPathParams(resolvePathParams(route, request));

		AspectChain chain = new AspectChainImpl(route);
		return chain.proceed(request, response);
	}

	private Map<String, PathParam> resolvePathParams(final EntityRoute route, final Request<?> request) {
		return StreamUtils
			.zip(route.getRouteParts().stream(), request.getPathParts().stream(), pathParamExtractor)
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(PathParam::getName, Function.identity()));
	}
}
