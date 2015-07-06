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

import static org.joyrest.utils.PathUtils.getPathParams;

import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.interceptor.InterceptorChainImpl;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.processor.ExceptionProcessor;
import org.joyrest.exception.processor.ExceptionProcessorImpl;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.resolver.DefaultRouteResolver;
import org.joyrest.routing.resolver.RouteResolver;

/**
 * {@inheritDoc}
 *
 * <p>
 * The processor gets {@link ApplicationContext} with all information which can be provided by framework regarding configured routes.
 * </p>
 *
 * @author pbouda
 */
public class RequestProcessorImpl implements RequestProcessor {

	/* Classes for route resolving - find the correct route according to the incoming model */
	private final RouteResolver defaultRouteResolver;

	/* Class for a exception processing */
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

		request.setPathParams(getPathParams(route, request.getPathParts()));

		InterceptorChain chain = new InterceptorChainImpl(route);
		return chain.proceed(request, response);
	}
}
