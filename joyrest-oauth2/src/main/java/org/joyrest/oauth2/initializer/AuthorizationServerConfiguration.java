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

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

public class AuthorizationServerConfiguration {

    private final UserDetailsService userDetailsService;
    private final ClientDetailsService clientDetailsService;
    private TokenStore tokenStore;
    private AuthorizationCodeServices authorizationCodeServices;

    public AuthorizationServerConfiguration(UserDetailsService userDetailsService, ClientDetailsService clientDetailsService) {
        this(new InMemoryTokenStore(), new InMemoryAuthorizationCodeServices(), userDetailsService, clientDetailsService);
    }

    public AuthorizationServerConfiguration(TokenStore tokenStore, AuthorizationCodeServices authorizationCodeServices,
                                            UserDetailsService userDetailsService, ClientDetailsService clientDetailsService) {
        this.tokenStore = tokenStore;
        this.authorizationCodeServices = authorizationCodeServices;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(final TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public AuthorizationCodeServices getAuthorizationCodeServices() {
        return authorizationCodeServices;
    }

    public void setAuthorizationCodeServices(final AuthorizationCodeServices authorizationCodeServices) {
        this.authorizationCodeServices = authorizationCodeServices;
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