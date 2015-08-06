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

import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.interceptor.InterceptorInternalOrders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationInterceptor implements Interceptor {

    private final static JoyLogger logger = JoyLogger.of(AuthenticationInterceptor.class);

    private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();

    private AuthenticationManager authenticationManager;

    @Override
    public InternalResponse<Object> around(InterceptorChain chain, InternalRequest<Object> req, InternalResponse<Object> resp) {

        Optional<Authentication> authentication = extract(req);
        req.setPrincipal(authentication.get());

        return chain.proceed(req, resp);
    }

    @Override
    public int getOrder() {
        return InterceptorInternalOrders.AUTHORIZATION;
    }

    public Optional<Authentication> extract(InternalRequest<Object> req) {
        return extractToken(req)
            .filter(Objects::nonNull)
            .map(token -> new PreAuthenticatedAuthenticationToken(token, ""));
    }

    private Optional<String> extractToken(InternalRequest<Object> req) {
        return extractHeaderToken(req)
            .filter(Objects::isNull)
            .flatMap(value -> req.getQueryParam(OAuth2AccessToken.ACCESS_TOKEN));
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
