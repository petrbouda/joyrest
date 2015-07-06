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

import static java.util.Collections.singletonList;

import org.joyrest.context.initializer.BeanFactory;
import org.joyrest.context.initializer.InitContext;
import org.joyrest.context.initializer.Initializer;
import org.joyrest.oauth2.configuration.JoyAuthorizationServerEndpointsConfiguration;
import org.joyrest.oauth2.configuration.JoyAuthorizationServerSecurityConfiguration;
import org.joyrest.oauth2.configuration.JoyWebSecurityConfiguration;
import org.joyrest.oauth2.interceptor.SpringSecurityChainInterceptor;

public class OAuth2Initializer implements Initializer {

	@Override
	public void init(InitContext context, BeanFactory beanFactory) {
		AuthorizationServerConfiguration authServerConfig = beanFactory.get(AuthorizationServerConfiguration.class);

		JoyAuthorizationServerEndpointsConfiguration endpointConfiguration =
				new JoyAuthorizationServerEndpointsConfiguration(authServerConfig);

		JoyAuthorizationServerSecurityConfiguration securityConfiguration =
				new JoyAuthorizationServerSecurityConfiguration(authServerConfig, endpointConfiguration);

		JoyWebSecurityConfiguration webSecurityConfiguration =
				new JoyWebSecurityConfiguration(singletonList(securityConfiguration));

		SpringSecurityChainInterceptor securityChainInterceptor =
				new SpringSecurityChainInterceptor(webSecurityConfiguration.springSecurityFilterChain());

		context.addControllerConfiguration(endpointConfiguration.tokenEndpoint());
		context.addInterceptor(securityChainInterceptor);
	}
}
