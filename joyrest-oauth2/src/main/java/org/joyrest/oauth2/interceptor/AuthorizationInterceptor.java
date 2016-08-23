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
package org.joyrest.oauth2.interceptor;

import java.util.Collections;
import java.util.List;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.interceptor.InterceptorInternalOrders;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

import static java.util.stream.Collectors.toList;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp)
            throws Exception {
        InternalRoute route = chain.getRoute();
        if (!route.isSecured()) {
            return chain.proceed(req, resp);
        }

        Authentication authentication = req.getPrincipal()
            .filter(principal -> principal instanceof Authentication)
            .map(principal -> (Authentication) principal)
            .orElseThrow(() -> new InvalidConfigurationException("Principal object is not Authentication type."));

        List<String> authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toList());

        if (Collections.disjoint(authorities, route.getRoles())) {
            throw new UserDeniedAuthorizationException("User denied access");
        }

        return chain.proceed(req, resp);
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.AUTHORIZATION;
    }
}
