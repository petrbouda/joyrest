package org.joyrest.processor;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.joyrest.exception.type.RestException.notFoundSupplier;

import java.util.Map;
import java.util.Objects;

import org.joyrest.aspect.AspectChain;
import org.joyrest.aspect.AspectChainImpl;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.processor.ExceptionProcessor;
import org.joyrest.exception.processor.ExceptionProcessorImpl;
import org.joyrest.extractor.PathParamExtractor;
import org.joyrest.model.http.PathParam;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
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

	/* Class for a ecxeption processing */
	private final ExceptionProcessor exceptionProcessor;

	public RequestProcessorImpl(ApplicationContext context) {
		this.defaultRouteResolver = new DefaultRouteResolver(context);
		this.exceptionProcessor = new ExceptionProcessorImpl(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternalResponse<Object> process(final InternalRequest<Object> request,
			final InternalResponse<Object> response) throws Exception {
		try {
			return processRequest(request, response);
		} catch (Exception ex) {
			return exceptionProcessor.process(ex, request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private InternalResponse<Object> processRequest(final InternalRequest<Object> request, final InternalResponse<Object> response) {
		final InternalRoute route = defaultRouteResolver.resolveRoute(request)
			.orElseThrow(notFoundSupplier(String.format(
					"No suitable route was found for path [%s] and method [%s]", request.getPath(), request.getMethod())));

		request.setPathParams(resolvePathParams(route, request));

		AspectChain chain = new AspectChainImpl(route);
		return chain.proceed(request, response);
	}

	private Map<String, PathParam> resolvePathParams(final InternalRoute route, final Request<?> request) {
		return StreamUtils
			.zip(route.getRouteParts().stream(), request.getPathParts().stream(), pathParamExtractor)
			.filter(Objects::nonNull)
			.collect(toMap(PathParam::getName, identity()));
	}
}
