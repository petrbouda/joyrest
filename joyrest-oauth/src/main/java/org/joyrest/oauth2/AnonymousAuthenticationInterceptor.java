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
package org.joyrest.oauth2;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.joyrest.aspect.Interceptor;
import org.joyrest.aspect.InterceptorChain;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.oauth2.model.JoyHttpServletRequestWrapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class AnonymousAuthenticationInterceptor implements Interceptor {

	private static final JoyLogger log = new JoyLogger(AnonymousAuthenticationInterceptor.class);

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private String key;
	private Object principal;
	private List<GrantedAuthority> authorities;

	/**
	 * Creates a filter with a principal named "anonymousUser", the single authority "ROLE_ANONYMOUS" and UUID key.
	 */
	public AnonymousAuthenticationInterceptor() {
		this(UUID.randomUUID().toString(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
	}

	/**
	 * Creates a filter with a principal named "anonymousUser" and the single authority "ROLE_ANONYMOUS".
	 *
	 * @param key the key to identify tokens created by this filter
	 */
	public AnonymousAuthenticationInterceptor(String key) {
		this(key, "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
	}

	/**
	 *
	 * @param key key the key to identify tokens created by this filter
	 * @param principal the principal which will be used to represent anonymous users
	 * @param authorities the authority list for anonymous users
	 */
	public AnonymousAuthenticationInterceptor(String key, Object principal, List<GrantedAuthority> authorities) {
		this.key = key;
		this.principal = principal;
		this.authorities = authorities;
	}

	@Override
	public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (isNull(auth)) {
			Authentication newAuth = createAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			log.debug(() -> "Populated SecurityContextHolder with anonymous token: '" + newAuth + "'");
		} else {
			log.debug(() -> "SecurityContextHolder not populated with anonymous token, as it already contained: '" + auth + "'");
		}

		return chain.proceed(req, resp);
	}

	protected Authentication createAuthentication(InternalRequest<?> request) {
		AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(key, principal, authorities);
		auth.setDetails(authenticationDetailsSource.buildDetails(new JoyHttpServletRequestWrapper(request)));
		return auth;
	}

	@Override
	public int getOrder() {
		return -50;
	}

	public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
