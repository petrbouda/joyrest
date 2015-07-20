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
package org.joyrest.model.request;

import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.http.PathParam;
import static org.joyrest.model.http.MediaType.WILDCARD;
import static org.joyrest.utils.CollectionUtils.nonEmpty;
import static org.joyrest.utils.PathUtils.createPathParts;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <E> typed of the entity which is wrapped by the provider object
 * @see ImmutableRequest
 * @see Request
 * @author pbouda
 */
public abstract class InternalRequest<E> implements Request<E> {

    protected Map<String, PathParam> pathParams;

    protected MediaType matchedAccept;

    protected List<String> pathParts;

    protected E entity;

    protected MediaType contentType;

    protected List<MediaType> accept;

    protected Principal principal;

    /**
     * Returns remote address of an incoming request
     *
     * @return incoming request's address
     */
    public abstract String getRemoteAddr();

    /**
     * Returns an inputstream object of an incoming entity
     *
     * @return incoming entity's input stream
     */
    public abstract InputStream getInputStream();

    /**
     * Returns a concrete header according to {@code name} value
     *
     * @param name header's name
     * @return header value in {@link Optional} object
     */
    public Optional<String> getHeader(String name) {
        return getHeader(HeaderName.of(name));
    }

    /**
     * Returns a content-type header value
     *
     * @return content-type header value in {@link Optional} object
     */
    public MediaType getContentType() {
        if (isNull(this.contentType)) {
            contentType = getHeader(HeaderName.CONTENT_TYPE)
                .map(MediaType::of)
                .orElse(WILDCARD);
        }

        return contentType;
    }

    /**
     * Returns a accept header value
     *
     * @return accept header value in {@link Optional} object
     */
    public List<MediaType> getAccept() {
        if (isNull(accept)) {
            List<MediaType> accepts = getHeader(HeaderName.ACCEPT)
                .map(MediaType::list)
                .get();

            this.accept = nonEmpty(accepts) ? accepts : singletonList(WILDCARD);
        }
        return accept;
    }

    @Override
    public Map<String, PathParam> getPathParams() {
        return pathParams == null ? new HashMap<>() : pathParams;
    }

    public void setPathParams(Map<String, PathParam> pathParams) {
        this.pathParams = pathParams;
    }

    @Override
    public String getPathParam(String name) {
        PathParam pathParam = pathParams.get(name);
        if (isNull(pathParam)) {
            throw new InvalidConfigurationException(
                format("There is no configured path param under the name '%s'", name));
        }

        return pathParam.getValue();
    }

    public List<String> getPathParts() {
        if (isNull(pathParts)) {
            requireNonNull(getPath(), "Path cannot be null in case of a PathPart's creating");
            pathParts = createPathParts(getPath());
        }
        return pathParts;
    }

    @Override
    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public MediaType getMatchedAccept() {
        return matchedAccept;
    }

    public void setMatchedAccept(MediaType matchedAccept) {
        this.matchedAccept = matchedAccept;
    }

    @Override
    public Optional<Principal> getPrincipal() {
        return Optional.ofNullable(principal);
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}