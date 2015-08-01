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
package org.joyrest.oauth2.endpoint;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;
import org.joyrest.utils.MapUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import static org.joyrest.utils.StringUtils.isEmpty;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AuthorizationEndpoint extends TypedControllerConfiguration {

    private final OAuth2RequestFactory requestFactory;

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationCodeServices authorizationCodeServices;

    private final RedirectResolver redirectResolver = new DefaultRedirectResolver();

    private final OAuth2RequestValidator requestValidator = new DefaultOAuth2RequestValidator();

    private final TokenStoreUserApprovalHandler userApprovalHandler;

    private final TokenGranter tokenGranter;

    private final Object implicitLock = new Object();

    public AuthorizationEndpoint(AuthorizationCodeServices authorizationCodeServices,
                                 ClientDetailsService clientDetailsService,
                                 TokenGranter tokenGranter,
	                             TokenStoreUserApprovalHandler userApprovalHandler,
                                 OAuth2RequestFactory requestFactory) {

	    this.userApprovalHandler = userApprovalHandler;
        this.authorizationCodeServices = authorizationCodeServices;
        this.clientDetailsService = clientDetailsService;
        this.tokenGranter = tokenGranter;
        this.requestFactory = requestFactory;
    }

    @Override
    protected void configure() {
        setControllerPath("oauth");

        get("authorize", (req, resp) -> {
            Map<String, String> parameters = MapUtils.createOneDimMap(req.getQueryParams());
            AuthorizationRequest authorizationRequest = requestFactory.createAuthorizationRequest(parameters);

            Set<String> responseTypes = authorizationRequest.getResponseTypes();
            if (!responseTypes.contains("token") && !responseTypes.contains("code")) {
                throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);
            }

            if (isNull(authorizationRequest.getClientId())) {
                throw new InvalidClientException("A client id must be provided");
            }

            ClientDetails client = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());

            String redirectUriParameter = authorizationRequest.getRequestParameters().get(OAuth2Utils.REDIRECT_URI);
            String resolvedRedirect = redirectResolver.resolveRedirect(redirectUriParameter, client);
            if (isEmpty(resolvedRedirect)) {
                throw new RedirectMismatchException(
                    "A redirectUri must be either supplied or preconfigured in the ClientDetails");
            }
            authorizationRequest.setRedirectUri(resolvedRedirect);

            requestValidator.validateScope(authorizationRequest, client);

            authorizationRequest = userApprovalHandler.checkForPreApproval(authorizationRequest, null);
            boolean approved = userApprovalHandler.isApproved(authorizationRequest, null);
            authorizationRequest.setApproved(approved);

            if (authorizationRequest.isApproved()) {
                if (responseTypes.contains("token")) {
                    resp.status(HttpStatus.FOUND);
                    resp.header(HeaderName.LOCATION, getImplicitGrantResponse(authorizationRequest));
                }
                if (responseTypes.contains("code")) {
                    resp.status(HttpStatus.FOUND);
                    resp.header(HeaderName.LOCATION, getAuthorizationCodeResponse(authorizationRequest));
                }
            }
        });
    }

    private OAuth2AccessToken getAccessTokenForImplicitGrant(TokenRequest tokenRequest, OAuth2Request storedOAuth2Request) {
        OAuth2AccessToken accessToken;
        // These 1 method calls have to be atomic, otherwise the ImplicitGrantService can have a race condition where
        // one thread removes the token request before another has a chance to redeem it.
        synchronized (this.implicitLock) {
            accessToken = tokenGranter.grant("implicit",
                new ImplicitTokenRequest(tokenRequest, storedOAuth2Request));
        }
        return accessToken;
    }

    // We can grant a token and return it with implicit approval.
    private String getImplicitGrantResponse(AuthorizationRequest authorizationRequest) {
        try {
            TokenRequest tokenRequest = requestFactory.createTokenRequest(authorizationRequest, "implicit");
            OAuth2Request storedOAuth2Request = requestFactory.createOAuth2Request(authorizationRequest);
            OAuth2AccessToken accessToken = getAccessTokenForImplicitGrant(tokenRequest, storedOAuth2Request);
            if (isNull(accessToken)) {
                throw new UnsupportedResponseTypeException("Unsupported response type: token");
            }
            return appendAccessToken(authorizationRequest, accessToken);
        } catch (OAuth2Exception e) {
            return getUnsuccessfulRedirect(authorizationRequest, e, true);
        }
    }

    private String getAuthorizationCodeResponse(AuthorizationRequest authorizationRequest) {
        try {
            return getSuccessfulRedirect(authorizationRequest, generateCode(authorizationRequest));
        } catch (OAuth2Exception e) {
            return getUnsuccessfulRedirect(authorizationRequest, e, false);
        }
    }

    private String appendAccessToken(AuthorizationRequest authorizationRequest, OAuth2AccessToken accessToken) {

        Map<String, Object> vars = new LinkedHashMap<>();
        Map<String, String> keys = new HashMap<>();

        if (isNull(accessToken)) {
            throw new InvalidRequestException("An implicit grant could not be made");
        }

        vars.put("access_token", accessToken.getValue());
        vars.put("token_type", accessToken.getTokenType());
        String state = authorizationRequest.getState();

        if (nonNull(state)) {
            vars.put("state", state);
        }

        Date expiration = accessToken.getExpiration();
        if (nonNull(expiration)) {
            long expires_in = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            vars.put("expires_in", expires_in);
        }

        String originalScope = authorizationRequest.getRequestParameters().get(OAuth2Utils.SCOPE);
        if (isNull(originalScope) || !OAuth2Utils.parseParameterList(originalScope).equals(accessToken.getScope())) {
            vars.put("scope", OAuth2Utils.formatParameterList(accessToken.getScope()));
        }

        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            Object value = additionalInformation.get(key);
            if (nonNull(value)) {
                keys.put("extra_" + key, key);
                vars.put("extra_" + key, value);
            }
        }
        // Do not include the refresh token (even if there is one)
        return append(authorizationRequest.getRedirectUri(), vars, keys, true);
    }

    private String generateCode(AuthorizationRequest authorizationRequest) throws AuthenticationException {
        try {
            OAuth2Request storedOAuth2Request = requestFactory.createOAuth2Request(authorizationRequest);
            OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, null);
            return authorizationCodeServices.createAuthorizationCode(combinedAuth);
        } catch (OAuth2Exception e) {
            if (authorizationRequest.getState() != null) {
                e.addAdditionalInformation("state", authorizationRequest.getState());
            }
            throw e;
        }
    }

    private String getSuccessfulRedirect(AuthorizationRequest authorizationRequest, String authorizationCode) {
        if (isNull(authorizationCode)) {
            throw new IllegalStateException("No authorization code found in the current request scope.");
        }

        Map<String, String> query = new LinkedHashMap<>();
        query.put("code", authorizationCode);

        String state = authorizationRequest.getState();
        if (nonNull(state)) {
            query.put("state", state);
        }

        return append(authorizationRequest.getRedirectUri(), query, false);
    }

    private String getUnsuccessfulRedirect(AuthorizationRequest authorizationRequest, OAuth2Exception failure, boolean fragment) {
        if (isNull(authorizationRequest) || isNull(authorizationRequest.getRedirectUri())) {
            // we have no redirect for the user. very sad.
            throw new UnapprovedClientAuthenticationException("Authorization failure, and no redirect URI.", failure);
        }

        Map<String, String> query = new LinkedHashMap<>();

        query.put("error", failure.getOAuth2ErrorCode());
        query.put("error_description", failure.getMessage());

        if (nonNull(authorizationRequest.getState())) {
            query.put("state", authorizationRequest.getState());
        }

        if (nonNull(failure.getAdditionalInformation())) {
            for (Map.Entry<String, String> additionalInfo : failure.getAdditionalInformation().entrySet()) {
                query.put(additionalInfo.getKey(), additionalInfo.getValue());
            }
        }

        return append(authorizationRequest.getRedirectUri(), query, fragment);
    }

    private String append(String base, Map<String, ?> query, boolean fragment) {
        return append(base, query, null, fragment);
    }

    private String append(String base, Map<String, ?> query, Map<String, String> keys, boolean fragment) {

        UriComponentsBuilder template = UriComponentsBuilder.newInstance();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base);
        URI redirectUri;
        try {
            // assume it's encoded to start with (if it came in over the wire)
            redirectUri = builder.build(true).toUri();
        } catch (Exception e) {
            // ... but allow client registrations to contain hard-coded non-encoded values
            redirectUri = builder.build().toUri();
            builder = UriComponentsBuilder.fromUri(redirectUri);
        }
        template.scheme(redirectUri.getScheme()).port(redirectUri.getPort()).host(redirectUri.getHost())
            .userInfo(redirectUri.getUserInfo()).path(redirectUri.getPath());

        if (fragment) {
            StringBuilder values = new StringBuilder();
            if (redirectUri.getFragment() != null) {
                String append = redirectUri.getFragment();
                values.append(append);
            }
            for (String key : query.keySet()) {
                if (values.length() > 0) {
                    values.append("&");
                }
                String name = key;
                if (keys != null && keys.containsKey(key)) {
                    name = keys.get(key);
                }
                values.append(name + "={" + key + "}");
            }
            if (values.length() > 0) {
                template.fragment(values.toString());
            }
            UriComponents encoded = template.build().expand(query).encode();
            builder.fragment(encoded.getFragment());
        } else {
            for (String key : query.keySet()) {
                String name = key;
                if (nonNull(keys) && keys.containsKey(key)) {
                    name = keys.get(key);
                }
                template.queryParam(name, "{" + key + "}");
            }
            template.fragment(redirectUri.getFragment());
            UriComponents encoded = template.build().expand(query).encode();
            builder.query(encoded.getQuery());
        }

        return builder.build().toUriString();
    }

}
