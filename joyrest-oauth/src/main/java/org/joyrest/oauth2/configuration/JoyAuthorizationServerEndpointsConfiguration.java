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
package org.joyrest.oauth2.configuration;

import org.joyrest.oauth2.endpoint.TokenEndpoint;
import org.joyrest.oauth2.initializer.AuthorizationServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class JoyAuthorizationServerEndpointsConfiguration {

	private final AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();

	public JoyAuthorizationServerEndpointsConfiguration(AuthorizationServerConfiguration configuration) {
		endpoints.setClientDetailsService(configuration.getClientDetailsService());
		endpoints.tokenStore(configuration.getTokenStore());

	}

	public TokenEndpoint tokenEndpoint() {
		try {
			TokenEndpoint tokenEndpoint = new TokenEndpoint();
			tokenEndpoint.setClientDetailsService(clientDetailsService());
			tokenEndpoint.setTokenGranter(tokenGranter());
			tokenEndpoint.setRequestFactory(oauth2RequestFactory());
			tokenEndpoint.setRequestValidator(oauth2RequestValidator());
			return tokenEndpoint;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot configure token endpoint.", e);
		}
	}

	public AuthorizationServerEndpointsConfigurer getEndpointsConfigurer() {
		if (!endpoints.isTokenServicesOverride())
			endpoints.tokenServices(defaultAuthorizationServerTokenServices());

		return endpoints;
	}

	public AuthorizationServerTokenServices defaultAuthorizationServerTokenServices() {
		return endpoints.getDefaultAuthorizationServerTokenServices();
	}

	public FrameworkEndpointHandlerMapping oauth2EndpointHandlerMapping() throws Exception {
		return getEndpointsConfigurer().getFrameworkEndpointHandlerMapping();
	}

	private OAuth2RequestFactory oauth2RequestFactory() throws Exception {
		return getEndpointsConfigurer().getOAuth2RequestFactory();
	}

	private ClientDetailsService clientDetailsService() throws Exception {
		return getEndpointsConfigurer().getClientDetailsService();
	}

	private OAuth2RequestValidator oauth2RequestValidator() throws Exception {
		return getEndpointsConfigurer().getOAuth2RequestValidator();
	}

	private TokenGranter tokenGranter() throws Exception {
		return getEndpointsConfigurer().getTokenGranter();
	}

}
