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
import java.util.Arrays;
import java.util.List;

import org.joyrest.context.initializer.BeanFactory;
import org.joyrest.context.initializer.InitContext;
import org.joyrest.context.initializer.Initializer;
import org.joyrest.oauth2.endpoint.TokenEndpoint;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class OAuth2Initializer implements Initializer {

    @Override
    public void init(InitContext context, BeanFactory beanFactory) {
        AuthorizationServerConfiguration authServerConfig = beanFactory.get(AuthorizationServerConfiguration.class);
        TokenStore tokenStore = authServerConfig.getTokenStore();
        UserDetailsService userService = authServerConfig.getUserDetailsService();
        ClientDetailsService clientService = authServerConfig.getClientDetailsService();

        PreAuthenticatedAuthenticationProvider preProvider = new PreAuthenticatedAuthenticationProvider();
        preProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper(userService));

        DaoAuthenticationProvider clientAuthProvider = new DaoAuthenticationProvider();
        clientAuthProvider.setUserDetailsService(new ClientDetailsUserDetailsService(clientService));

        DaoAuthenticationProvider userAuthProvider = new DaoAuthenticationProvider();
        userAuthProvider.setUserDetailsService(userService);

        ProviderManager clientProviderManager = new ProviderManager(singletonList(clientAuthProvider));
        ProviderManager userProviderManager = new ProviderManager(asList(userAuthProvider, preProvider));

        TokenEndpoint tokenEndpoint = new TokenEndpoint(clientProviderManager, clientService,
            compositeTokenGranter(clientService, userProviderManager, tokenStore));

        context.addControllerConfiguration(tokenEndpoint);
        //        context.addControllerConfiguration(endpointConfiguration.authorizationEndpoint());
    }

    private TokenGranter compositeTokenGranter(ClientDetailsService clientService, AuthenticationManager manager,
                                               TokenStore tokenStore) {
        DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientService);
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientService);
        tokenServices.setAuthenticationManager(manager);
        tokenServices.setTokenStore(tokenStore);

        List<TokenGranter> granters = new ArrayList<>();
        granters.add(new ClientCredentialsTokenGranter(tokenServices, clientService, requestFactory));
        granters.add(new ImplicitTokenGranter(tokenServices, clientService, requestFactory));
        granters.add(new ResourceOwnerPasswordTokenGranter(manager, tokenServices, clientService, requestFactory));
        granters.add(new RefreshTokenGranter(tokenServices, clientService, requestFactory));
        //        granters.add(new AuthorizationCodeTokenGranter());
        return new CompositeTokenGranter(granters);
    }
}
