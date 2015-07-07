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
package org.joyrest.oauth2.endpoint;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.joyrest.model.response.Response;
import org.joyrest.routing.TypedControllerConfiguration;
import org.joyrest.utils.MapUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.routing.entity.ResponseType.Resp;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.isEmpty;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TokenEndpoint extends TypedControllerConfiguration {

    private ClientDetailsService clientDetailsService;

    private TokenGranter tokenGranter;

    private OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);

    private OAuth2RequestValidator requestValidator = new DefaultOAuth2RequestValidator();

    private static String getClientId(Principal principal) {
        Authentication client = (Authentication) principal;

        String clientId = client.getName();
        if (client instanceof OAuth2Authentication)
        // Might be a client and user combined authentication
        {
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }

        return clientId;
    }

    private static Supplier<InsufficientAuthenticationException> insufficientAuthenticationExceptionSupplier() {
        return () -> new InsufficientAuthenticationException(
            "There is no client authentication. Try adding an appropriate authentication filter.");
    }

    @Override
    protected void configure() {
        setControllerPath("oauth");

        post("token", (req, resp) -> {
            Principal principal = req.getPrincipal()
                .orElseThrow(insufficientAuthenticationExceptionSupplier());

            if (!(principal instanceof Authentication)) {
                throw insufficientAuthenticationExceptionSupplier().get();
            }

            String clientId = getClientId(principal);
            ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

            Map<String, String> parameters = MapUtils.createOneDimMap(req.getQueryParams());
            TokenRequest tokenRequest = requestFactory.createTokenRequest(parameters, authenticatedClient);
            if (!isEmpty(clientId))
            // Only validate the client details if a client authenticated during this request.
            {
                if (!clientId.equals(tokenRequest.getClientId())) {
                    throw new InvalidClientException("Given client ID does not match authenticated client");
                }
            }

            if (nonNull(authenticatedClient)) {
                requestValidator.validateScope(tokenRequest, authenticatedClient);
            }

            if (!hasText(tokenRequest.getGrantType())) {
                throw new InvalidRequestException("Missing grant type");
            }

            if (tokenRequest.getGrantType().equals("implicit")) {
                throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
            }

            if (isAuthCodeRequest(parameters))
            // The scope was requested or determined during the authorization step
            {
                if (!tokenRequest.getScope().isEmpty()) {
                    tokenRequest.setScope(Collections.emptySet());
                }
            }

            if (isRefreshTokenRequest(parameters))
            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
            {
                tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
            }

            OAuth2AccessToken token = tokenGranter.grant(tokenRequest.getGrantType(), tokenRequest);
            if (isNull(token)) {
                throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
            }

            createResponse(resp, token);

        }, Resp(OAuth2AccessToken.class)).produces(JSON);
    }

    private void createResponse(Response<OAuth2AccessToken> resp, OAuth2AccessToken accessToken) {
        resp.header("Cache-Control", "no-store");
        resp.header("Pragma", "no-cache");
        resp.entity(accessToken);
    }

    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    public void setTokenGranter(TokenGranter tokenGranter) {
        this.tokenGranter = tokenGranter;
    }

    public void setRequestFactory(OAuth2RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public void setRequestValidator(OAuth2RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }
}
