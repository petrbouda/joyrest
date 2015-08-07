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

import java.util.Objects;
import java.util.Optional;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.interceptor.InterceptorInternalOrders;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.InternalRoute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationInterceptor implements Interceptor {

    private final AuthenticationManager authenticationManager;

    public AuthenticationInterceptor(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp)
            throws Exception{
        InternalRoute route = chain.getRoute();

        if (route.isSecured()) {
            Authentication authentication = extractToken(req)
                .orElseThrow(() -> new InvalidTokenException("There is no access token in headers or in query params"));

            Authentication principal = authenticationManager.authenticate(authentication);
            req.setPrincipal(principal);
        }

        return chain.proceed(req, resp);
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.AUTHENTICATION;
    }

    private Optional<Authentication> extractToken(InternalRequest<Object> req) {
        return extractHeaderToken(req)
            .filter(Objects::isNull)
            .flatMap(value -> req.getQueryParam(OAuth2AccessToken.ACCESS_TOKEN))
            .filter(Objects::nonNull)
            .map(token -> new PreAuthenticatedAuthenticationToken(token, ""));
    }

    private Optional<String> extractHeaderToken(InternalRequest<Object> req) {
        return req.getHeader("Authorization")
            .filter(value -> value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))
            .map(value -> value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim())
            .map(value -> {
                int commaIndex = value.indexOf(',');
                if (commaIndex > 0) {
                    return value.substring(0, commaIndex);
                }
                return null;
            });
    }

}
