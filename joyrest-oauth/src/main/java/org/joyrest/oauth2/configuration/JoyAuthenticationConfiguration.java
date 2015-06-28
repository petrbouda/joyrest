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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JoyAuthenticationConfiguration {

	public static AuthenticationManager inMemoryAuthenticationManager() {
		try {
			AuthenticationManagerBuilder authBuilder = new AuthenticationManagerBuilder(new InitializedBeanPostProcessor());
			authBuilder.inMemoryAuthentication();
			return authBuilder.build();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot configure an authentication manager.", e);
		}
	}

	public static AuthenticationManager jdbcAuthenticationManager() {
		try {
			AuthenticationManagerBuilder authBuilder = new AuthenticationManagerBuilder(new InitializedBeanPostProcessor());
			authBuilder.jdbcAuthentication();
			return authBuilder.build();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot configure an authentication manager.", e);
		}
	}

	public static AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		try {
			AuthenticationManagerBuilder authBuilder = new AuthenticationManagerBuilder(new InitializedBeanPostProcessor());
			authBuilder.userDetailsService(userDetailsService);
			return authBuilder.build();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot configure an authentication manager.", e);
		}
	}
}