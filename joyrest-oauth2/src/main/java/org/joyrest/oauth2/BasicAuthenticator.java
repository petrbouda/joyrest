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
package org.joyrest.oauth2;

import java.io.IOException;

import org.joyrest.logging.JoyLogger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static java.util.Objects.isNull;

public class BasicAuthenticator {

    private static final JoyLogger logger = new JoyLogger(BasicAuthenticator.class);
    private static final String CREDENTIALS_CHARSET = "UTF-8";

    private final AuthenticationManager authenticationManager;

    public BasicAuthenticator(final AuthenticationManager oAuth2AuthenticationManager) {
        this.authenticationManager = oAuth2AuthenticationManager;
    }

    public Authentication authenticate(String header) {
        if (isNull(header) || !header.startsWith("Basic ")) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        try {
            String[] tokens = extractAndDecodeHeader(header);
            String username = tokens[0];

            logger.debug(() -> "Basic Authentication Authorization header found for user '" + username + "'");

            UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, tokens[1]);

            Authentication authentication = authenticationManager.authenticate(authRequest);
            logger.debug(() -> "Authentication success: " + authentication);
            return authentication;
        } catch (IOException ex) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid Base64
     */
    private String[] extractAndDecodeHeader(String header) throws IOException {

        byte[] base64Token = header.substring(6).getBytes(CREDENTIALS_CHARSET);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, CREDENTIALS_CHARSET);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] {token.substring(0, delim), token.substring(delim + 1)};
    }

}
