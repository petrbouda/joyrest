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

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import static java.util.Objects.requireNonNull;

public class JdbcClientDetailsServiceConfigurer extends AbstractClientDetailsServiceConfigurer {

    private final Set<ClientDetails> clientDetails = new HashSet<>();

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    public JdbcClientDetailsServiceConfigurer(DataSource dataSource) {
        this(dataSource, new StandardPasswordEncoder());
    }

    public JdbcClientDetailsServiceConfigurer(DataSource dataSource, PasswordEncoder passwordEncoder) {
        requireNonNull(dataSource);
        requireNonNull(passwordEncoder);

        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void addClient(ClientDetails value) {
        clientDetails.add(value);
    }

    @Override
    protected ClientDetailsService performBuild() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        clientDetails.forEach(clientDetailsService::addClientDetails);
        return clientDetailsService;
    }
}