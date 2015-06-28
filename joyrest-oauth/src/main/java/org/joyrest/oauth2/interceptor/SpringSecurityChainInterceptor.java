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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.oauth2.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import org.joyrest.aspect.Interceptor;
import org.joyrest.aspect.InterceptorChain;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.oauth2.context.JoySecurityContextHolder;
import org.joyrest.oauth2.model.JoyHttpServletRequestWrapper;
import org.joyrest.oauth2.model.JoyHttpServletResponseWrapper;
import org.joyrest.oauth2.model.NoopFilterChain;
import org.springframework.security.core.Authentication;

public class SpringSecurityChainInterceptor implements Interceptor {

	private final Filter springSecurityFilterChain;

	public SpringSecurityChainInterceptor(Filter springSecurityFilterChain) {
		this.springSecurityFilterChain = springSecurityFilterChain;
	}

	@Override
	public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp) {
		try {
			springSecurityFilterChain.doFilter(
				new JoyHttpServletRequestWrapper(req), new JoyHttpServletResponseWrapper(resp), new NoopFilterChain());

			Authentication authentication = JoySecurityContextHolder.getContext().getAuthentication();
			req.setPrincipal(authentication);
		} catch (IOException | ServletException e) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during processing OAuth2 Security.", e);
		}

		return chain.proceed(req, resp);
	}

	@Override
	public int getOrder() {
		return -50;
	}
}
