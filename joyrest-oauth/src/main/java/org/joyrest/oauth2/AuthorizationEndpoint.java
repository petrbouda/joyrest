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

import java.util.Map;
import java.util.Set;

import org.joyrest.routing.TypedControllerConfiguration;
import org.joyrest.utils.MapUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import static java.util.Objects.isNull;

public class AuthorizationEndpoint extends TypedControllerConfiguration {

	private final OAuth2RequestFactory requestFactory;

	public AuthorizationEndpoint(ClientDetailsService clientDetailsService) {
		this.requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
	}

	@Override
	protected void configure() {
		setControllerPath("oauth2");

		get("authorize", (req, resp) -> {
			Map<String, String> parameters = MapUtils.createOneDimMap(req.getQueryParams());
			AuthorizationRequest authorizationRequest = requestFactory.createAuthorizationRequest(parameters);

			Set<String> responseTypes = authorizationRequest.getResponseTypes();
			if (!responseTypes.contains("token") && !responseTypes.contains("code"))
				throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);

			if (isNull(authorizationRequest.getClientId()))
				throw new InvalidClientException("A client id must be provided");


		});
	}

}
