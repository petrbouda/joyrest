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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.oauth2.initializer;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Objects.requireNonNull;

public class AuthorizationServerConfiguration {

    private UserDetailsService userDetailsService = new InMemoryUserDetailsManager(new ArrayList<>());
    private ClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
    private TokenStore tokenStore = new InMemoryTokenStore();
    private AuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public AuthorizationServerConfiguration dataSource(final DataSource dataSource) {
        requireNonNull(dataSource);
        this.dataSource = dataSource;
        return this;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public AuthorizationServerConfiguration userDetailsService(final UserDetailsService userDetailsService) {
        requireNonNull(userDetailsService);
        this.userDetailsService = userDetailsService;
        return this;
    }

    public ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public AuthorizationServerConfiguration clientDetailsService(final ClientDetailsService clientDetailsService) {
        requireNonNull(clientDetailsService);
        this.clientDetailsService = clientDetailsService;
        return this;
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public AuthorizationServerConfiguration tokenStore(final TokenStore tokenStore) {
        requireNonNull(tokenStore);
        this.tokenStore = tokenStore;
        return this;
    }

    public AuthorizationCodeServices getAuthorizationCodeServices() {
        return authorizationCodeServices;
    }

    public AuthorizationServerConfiguration authorizationCodeServices(final AuthorizationCodeServices authorizationCodeServices) {
        requireNonNull(authorizationCodeServices);
        this.authorizationCodeServices = authorizationCodeServices;
        return this;
    }
}