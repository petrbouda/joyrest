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
package org.joyrest.oauth2.handler;

import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.oauth2.exception.ForbiddenException;
import org.joyrest.oauth2.exception.UnauthorizedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import static org.joyrest.model.http.HeaderName.CACHE_CONTROL;
import static org.joyrest.model.http.HeaderName.PRAGMA;
import static org.joyrest.model.http.HeaderName.WWW_AUTHENTICATE;
import static org.joyrest.routing.entity.ResponseType.Resp;

public class OAuthExceptionConfiguration extends TypedExceptionConfiguration {

    @Override
    protected void configure() {
        handle(OAuth2Exception.class, this::process, Resp(OAuth2Exception.class));

        handle(AuthenticationException.class, (req, resp, ex) -> {
            process(req, resp, new UnauthorizedException(ex.getMessage(), ex));
        }, Resp(OAuth2Exception.class));

        handle(AccessDeniedException.class, (req, resp, ex) -> {
            process(req, resp, new ForbiddenException(ex.getMessage(), ex));
        }, Resp(OAuth2Exception.class));
    }

    private void process(Request<?> req, Response<OAuth2Exception> resp, OAuth2Exception ex) {
        resp.entity(ex);

        int status = ex.getHttpErrorCode();
        resp.status(HttpStatus.of(status));

        resp.header(CACHE_CONTROL, "no-store");
        resp.header(PRAGMA, "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.code() || (ex instanceof InsufficientScopeException)) {
            resp.header(WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, ex.getSummary()));
        }
    }
}
