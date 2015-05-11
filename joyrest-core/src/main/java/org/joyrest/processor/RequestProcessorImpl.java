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
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.resolver.DefaultRouteResolver;
import org.joyrest.routing.resolver.RouteResolver;

import com.codepoetics.protonpack.StreamUtils;

/**
 * {@inheritDoc}
 *
 * <p/>
 * The processor gets {@link ApplicationContext} with all information which can be provided by framework regarding configured
 * routes.
 *
 * @author pbouda
 */
public class RequestProcessorImpl implements RequestProcessor {

	/* Class is able to extract path params from incoming path according an info from route */
	private final PathParamExtractor pathParamExtractor = new PathParamExtractor();

	/* Classes for route resolving - find the correct route according to the incoming model */
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
	public void process(final InternalRequest<Object> request, final InternalResponse<Object> response) throws Exception {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			exceptionProcessor.process(ex, request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private InternalResponse<Object> processRequest(final InternalRequest<Object> request, final InternalResponse<Object> response) {
		final InternalRoute route = defaultRouteResolver.resolveRoute(request);

		request.setPathParams(resolvePathParams(route, request));

		AspectChain chain = new AspectChainImpl(route);
		return chain.proceed(request, response);
	}

	private Map<String, PathParam> resolvePathParams(final InternalRoute route, final InternalRequest<?> request) {
		return StreamUtils
			.zip(route.getRouteParts().stream(), request.getPathParts().stream(), pathParamExtractor)
			.filter(Objects::nonNull)
			.collect(toMap(PathParam::getName, identity()));
	}
}
