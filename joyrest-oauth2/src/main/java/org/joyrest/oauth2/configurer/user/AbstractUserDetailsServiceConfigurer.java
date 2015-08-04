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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public abstract class AbstractUserDetailsServiceConfigurer {

    private List<UserBuilder> userBuilders = new ArrayList<>();

    public UserBuilder withUser(String username, String password) {
        UserBuilder userBuilder = new UserBuilder(username, password);
        this.userBuilders.add(userBuilder);
        return userBuilder;
    }

    public UserDetailsService build() throws Exception {
        userBuilders.forEach(builder -> addUser(builder.build()));
        return performBuild();
    }

    abstract protected void addUser(UserDetails build);

    abstract protected UserDetailsService performBuild();

    public class UserBuilder {
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;

        private UserBuilder(String username, String password) {
            requireNonNull(username);
            requireNonNull(password);

            this.username = username;
            this.password = password;
        }

        public AbstractUserDetailsServiceConfigurer and() {
            return AbstractUserDetailsServiceConfigurer.this;
        }

        public UserBuilder roles(String... roles) {
            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                .peek(role -> Assert.isTrue(!role.startsWith("ROLE_"),
                    role + " cannot start with ROLE_ (it is automatically added)"))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(toList());
            return authorities(authorities);
        }

        public UserBuilder authorities(GrantedAuthority...authorities) {
            return authorities(Arrays.asList(authorities));
        }

        public UserBuilder authorities(List<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public UserBuilder authorities(String... authorities) {
            return authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        public UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        private UserDetails build() {
            return new User(username, password, !disabled, !accountExpired,
                !credentialsExpired, !accountLocked, authorities);
        }
    }
}
