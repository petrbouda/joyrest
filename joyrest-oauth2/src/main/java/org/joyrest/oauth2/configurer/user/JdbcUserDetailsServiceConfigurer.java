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
package org.joyrest.oauth2.configurer.user;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.joyrest.oauth2.configurer.client.AbstractClientDetailsServiceConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import static java.util.Objects.requireNonNull;

public class JdbcUserDetailsServiceConfigurer extends AbstractUserDetailsServiceConfigurer {

    private Set<UserDetails> userDetails = new HashSet<>();

    private DataSource dataSource;

    public JdbcUserDetailsServiceConfigurer(DataSource dataSource) {
        requireNonNull(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    protected void addUser(final UserDetails details) {
        userDetails.add(details);
    }

    @Override
    protected UserDetailsService performBuild() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);

        userDetails.forEach(userDetailsManager::createUser);
        return userDetailsManager;
    }
}