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
package org.joyrest.ittest.oauth;

import org.joyrest.ittest.oauth.services.FeedService;
import org.joyrest.ittest.oauth.services.FeedServiceImpl;
import org.joyrest.oauth2.configurer.user.InMemoryUserDetailsServiceConfigurer;
import org.joyrest.oauth2.initializer.AuthorizationServerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@Configuration
public class InMemoryOAuthConfiguration {

    @Bean
    FeedController oauthFeedController() {
        return new FeedController();
    }

    @Bean
    FeedService oauthFeedService() {
        return new FeedServiceImpl();
    }

    @Bean
    AuthorizationServerConfiguration authorizationServerConfiguration() throws Exception {
        return new AuthorizationServerConfiguration()
            .clientDetailsService(clientDetailsService())
            .userDetailsService(userDetailsService());
    }

    private ClientDetailsService clientDetailsService() throws Exception {
        // @formatter:off
		return new InMemoryClientDetailsServiceBuilder()
			.withClient("clientapp")
			    .authorizedGrantTypes("password", "refresh_token")
			    .authorities("USER")
			    .scopes("read", "write")
			    .secret("123456")
			.and()
			.withClient("clientapp2")
			    .authorizedGrantTypes("client_credentials")
			    .authorities("USER")
			    .scopes("read", "write")
			    .secret("123456")
			.and()
			.withClient("clientapp3")
			    .authorizedGrantTypes("authorization_code")
			    .authorities("USER")
			    .redirectUris(
                     "http://localhost:5000/login.html",
                     "http://localhost:5000/diff_login.html")
			    .scopes("read", "write")
			    .secret("123456")
			.and().build();
		// @formatter:on
    }

    private UserDetailsService userDetailsService() throws Exception {
        // @formatter:off
		return new InMemoryUserDetailsServiceConfigurer()
		    .withUser("roy", "spring")
				.roles("USER")
	        .and()
	        .withUser("craig", "spring")
				.roles("ADMIN")
	        .and()
			.withUser("greg", "spring")
	            .roles("GUEST")
			.and().build();
		// @formatter:on
    }
}