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
package org.joyrest.oauth2.configurer.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import static java.util.Collections.addAll;

public abstract class AbstractClientDetailsServiceConfigurer {

    private List<ClientBuilder> clientBuilders = new ArrayList<>();

    public ClientBuilder withClient(String clientId) {
        ClientBuilder clientBuilder = new ClientBuilder(clientId);
        this.clientBuilders.add(clientBuilder);
        return clientBuilder;
    }

    public ClientDetailsService build() throws Exception {
        clientBuilders.forEach(builder -> addClient(builder.build()));
        return performBuild();
    }

    abstract protected void addClient(ClientDetails build);

    abstract protected ClientDetailsService performBuild();

    public final class ClientBuilder {
        private final String clientId;
        private Collection<String> authorizedGrantTypes = new LinkedHashSet<>();
        private Collection<String> authorities = new LinkedHashSet<>();
        private Integer accessTokenValiditySeconds;
        private Integer refreshTokenValiditySeconds;
        private Collection<String> scopes = new LinkedHashSet<>();
        private Collection<String> autoApproveScopes = new HashSet<>();
        private String secret;
        private Set<String> registeredRedirectUris = new HashSet<>();
        private Set<String> resourceIds = new HashSet<>();
        private boolean autoApprove = true;
        private Map<String, Object> additionalInformation = new LinkedHashMap<>();

        private ClientBuilder(String clientId) {
            this.clientId = clientId;
        }

        private ClientDetails build() {
            BaseClientDetails result = new BaseClientDetails();
            result.setClientId(clientId);
            result.setAuthorizedGrantTypes(authorizedGrantTypes);
            result.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
            result.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
            result.setRegisteredRedirectUri(registeredRedirectUris);
            result.setClientSecret(secret);
            result.setScope(scopes);
            result.setAuthorities(AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()])));
            result.setResourceIds(resourceIds);
            result.setAdditionalInformation(additionalInformation);
            if (autoApprove) {
                result.setAutoApproveScopes(scopes);
            } else {
                result.setAutoApproveScopes(autoApproveScopes);
            }
            return result;
        }

        public ClientBuilder resourceIds(String... resourceIds) {
            addAll(this.resourceIds, resourceIds);
            return this;
        }

        public ClientBuilder redirectUris(String... registeredRedirectUris) {
            addAll(this.registeredRedirectUris, registeredRedirectUris);
            return this;
        }

        public ClientBuilder authorizedGrantTypes(String... authorizedGrantTypes) {
            addAll(this.authorizedGrantTypes, authorizedGrantTypes);
            return this;
        }

        public ClientBuilder accessTokenValiditySeconds(int accessTokenValiditySeconds) {
            this.accessTokenValiditySeconds = accessTokenValiditySeconds;
            return this;
        }

        public ClientBuilder refreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
            this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
            return this;
        }

        public ClientBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public ClientBuilder scopes(String... scopes) {
            addAll(this.scopes, scopes);
            return this;
        }

        public ClientBuilder authorities(String... authorities) {
            addAll(this.authorities, authorities);
            return this;
        }

        public ClientBuilder autoApprove(boolean autoApprove) {
            this.autoApprove = autoApprove;
            return this;
        }

        public ClientBuilder autoApprove(String... scopes) {
            addAll(this.autoApproveScopes, scopes);
            return this;
        }

        public ClientBuilder additionalInformation(Map<String, ?> map) {
            this.additionalInformation.putAll(map);
            return this;
        }

        public ClientBuilder additionalInformation(String... pairs) {
            for (String pair : pairs) {
                String separator = ":";
                if (!pair.contains(separator) && pair.contains("=")) {
                    separator = "=";
                }
                int index = pair.indexOf(separator);
                String key = pair.substring(0, index > 0 ? index : pair.length());
                String value = index > 0 ? pair.substring(index + 1) : null;
                this.additionalInformation.put(key, value);
            }
            return this;
        }

        public AbstractClientDetailsServiceConfigurer and() {
            return AbstractClientDetailsServiceConfigurer.this;
        }

    }
}
