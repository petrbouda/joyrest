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
package org.joyrest.model.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class HeaderName {

    public static final HeaderName ACCEPT = new HeaderName("accept");
    public static final HeaderName ACCEPT_CHARSET = new HeaderName("accept-charset");
    public static final HeaderName ACCEPT_ENCODING = new HeaderName("accept-encoding");
    public static final HeaderName ACCEPT_LANGUAGE = new HeaderName("accept-language");
    public static final HeaderName ALLOW = new HeaderName("allow");
    public static final HeaderName AUTHORIZATION = new HeaderName("authorization");
    public static final HeaderName CACHE_CONTROL = new HeaderName("cache-control");
    public static final HeaderName CONNECTION = new HeaderName("connection");
    public static final HeaderName CONTENT_ENCODING = new HeaderName("content-encoding");
    public static final HeaderName CONTENT_LANGUAGE = new HeaderName("content-language");
    public static final HeaderName CONTENT_LENGTH = new HeaderName("content-length");
    public static final HeaderName CONTENT_LOCATION = new HeaderName("content-location");
    public static final HeaderName CONTENT_MD5 = new HeaderName("content-md5");
    public static final HeaderName CONTENT_RANGE = new HeaderName("content-range");
    public static final HeaderName CONTENT_TYPE = new HeaderName("content-type");
    public static final HeaderName DATE = new HeaderName("date");
    public static final HeaderName EXPIRES = new HeaderName("expires");
    public static final HeaderName FROM = new HeaderName("from");
    public static final HeaderName HOST = new HeaderName("host");
    public static final HeaderName IF = new HeaderName("if");
    public static final HeaderName IF_MATCH = new HeaderName("if-match");
    public static final HeaderName IF_MODIFIED_SINCE = new HeaderName("if-modified-since");
    public static final HeaderName IF_NONE_MATCH = new HeaderName("if-none-match");
    public static final HeaderName IF_RANGE = new HeaderName("if-range");
    public static final HeaderName IF_UNMODIFIED_SINCE = new HeaderName("if-unmodified-since");
    public static final HeaderName LAST_MODIFIED = new HeaderName("last-modified");
    public static final HeaderName LOCATION = new HeaderName("location");
    public static final HeaderName PRAGMA = new HeaderName("pragma");
    public static final HeaderName PROXY_AUTHENTICATE = new HeaderName("proxy-authenticate");
    public static final HeaderName PROXY_AUTHORIZATION = new HeaderName("proxy-authorization");
    public static final HeaderName REFERER = new HeaderName("referer");
    public static final HeaderName RETRY_AFTER = new HeaderName("retry-after");
    public static final HeaderName SERVER = new HeaderName("server");
    public static final HeaderName TIMEOUT = new HeaderName("timeout");
    public static final HeaderName TRANSFER_ENCODING = new HeaderName("transfer-encoding");
    public static final HeaderName UPGRADE = new HeaderName("upgrade");
    public static final HeaderName USER_AGENT = new HeaderName("user-agent");
    public static final HeaderName WWW_AUTHENTICATE = new HeaderName("www-authenticate");

    private static final Map<String, HeaderName> cache;

    static {
        cache = new HashMap<>();
        cache.put(ACCEPT.getValue(), ACCEPT);
        cache.put(CONTENT_LENGTH.getValue(), CONTENT_LENGTH);
        cache.put(CONTENT_TYPE.getValue(), CONTENT_TYPE);
        cache.put(AUTHORIZATION.getValue(), AUTHORIZATION);
        cache.put(ALLOW.getValue(), ALLOW);
        cache.put(CACHE_CONTROL.getValue(), CACHE_CONTROL);
        cache.put(CONNECTION.getValue(), CONNECTION);
        cache.put(AUTHORIZATION.getValue(), AUTHORIZATION);
        cache.put(CONTENT_MD5.getValue(), CONTENT_MD5);
        cache.put(EXPIRES.getValue(), EXPIRES);
        cache.put(HOST.getValue(), HOST);
        cache.put(USER_AGENT.getValue(), USER_AGENT);
        cache.put(LOCATION.getValue(), LOCATION);
        cache.put(DATE.getValue(), DATE);
    }

    private final String value;

    private HeaderName(String value) {
        this.value = value;
    }

    public static final HeaderName of(String value) {
        requireNonNull(value);
        String lowerValue = value.toLowerCase();
        if (cache.containsKey(lowerValue)) {
            return cache.get(lowerValue);
        }
        return new HeaderName(lowerValue);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final HeaderName other = (HeaderName) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}