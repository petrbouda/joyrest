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
package org.joyrest.oauth2.initializer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Objects;

public class AuthorizationServerConfiguration {

	private final TokenStore tokenStore;

	private final UserDetailsService userDetailsService;

	private final ClientDetailsService clientDetailsService;

	public AuthorizationServerConfiguration(UserDetailsService userDetailsService, ClientDetailsService clientDetailsService) {
		this(new InMemoryTokenStore() ,userDetailsService, clientDetailsService);
	}

	public AuthorizationServerConfiguration(TokenStore tokenStore,
		UserDetailsService userDetailsService, ClientDetailsService clientDetailsService) {

		this.tokenStore = tokenStore;
		this.userDetailsService = userDetailsService;
		this.clientDetailsService = clientDetailsService;
	}

	public TokenStore getTokenStore() {
		return tokenStore;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tokenStore, userDetailsService, clientDetailsService);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final AuthorizationServerConfiguration other = (AuthorizationServerConfiguration) obj;
		return Objects.equals(this.tokenStore, other.tokenStore)
			&& Objects.equals(this.userDetailsService, other.userDetailsService)
			&& Objects.equals(this.clientDetailsService, other.clientDetailsService);
	}
}