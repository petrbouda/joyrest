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

import org.joyrest.oauth2.InitializedBeanPostProcessor;
import org.joyrest.oauth2.context.AuthorizationContext;
import org.joyrest.oauth2.filter.PrincipalPersistenceFilter;
import org.joyrest.oauth2.initializer.AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.web.session.SessionManagementFilter;

public class JoyAuthorizationServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthorizationServerSecurityConfigurer securityConfigurer = new AuthorizationServerSecurityConfigurer();

    private final AuthorizationServerEndpointsConfigurer endpointsConfigurer;

    private final JoyAuthorizationServerEndpointsConfiguration endpoints;

    private final ClientDetailsService clientDetailsService;

    private final UserDetailsService userDetailsService;

    public JoyAuthorizationServerSecurityConfiguration(AuthorizationServerConfiguration configuration,
                                                       JoyAuthorizationServerEndpointsConfiguration endpoints) {
        try {
            setApplicationContext(AuthorizationContext.getInstance());
            setObjectPostProcessor(new InitializedBeanPostProcessor());

            this.endpoints = endpoints;
            this.endpointsConfigurer = endpoints.getEndpointsConfigurer();
            this.clientDetailsService = configuration.getClientDetailsService();
            this.userDetailsService = configuration.getUserDetailsService();

            endpointsConfigurer.setClientDetailsService(clientDetailsService);
            endpointsConfigurer.authenticationManager(authenticationManager());
        } catch (Exception e) {
            throw new IllegalStateException("Cannot configure server security", e);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        AuthorizationContext.getInstance().putBean(AuthenticationManagerBuilder.class, auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FrameworkEndpointHandlerMapping handlerMapping = endpoints.oauth2EndpointHandlerMapping();
        String tokenEndpointPath = handlerMapping.getServletPath("/oauth/token");
        String tokenKeyPath = handlerMapping.getServletPath("/oauth/token_key");
        String checkTokenPath = handlerMapping.getServletPath("/oauth/check_token");

        http.setSharedObject(FrameworkEndpointHandlerMapping.class, handlerMapping);
        http.apply(securityConfigurer);
        http.addFilterBefore(new PrincipalPersistenceFilter(), SessionManagementFilter.class);

        if (!endpointsConfigurer.isUserDetailsServiceOverride()) {
            UserDetailsService userDetailsService = http.getSharedObject(UserDetailsService.class);
            endpointsConfigurer.userDetailsService(userDetailsService);
        }

        http.authorizeRequests()
            .antMatchers(tokenEndpointPath).fullyAuthenticated()
            .antMatchers(tokenKeyPath).access(securityConfigurer.getTokenKeyAccess())
            .antMatchers(checkTokenPath).access(securityConfigurer.getCheckTokenAccess())
            .and()
            .requestMatchers()
            .antMatchers(tokenEndpointPath, tokenKeyPath, checkTokenPath)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http.setSharedObject(ClientDetailsService.class, clientDetailsService);
    }
}
