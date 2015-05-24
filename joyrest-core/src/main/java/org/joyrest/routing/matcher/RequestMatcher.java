/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing.matcher;

import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.joyrest.model.http.MediaType.WILDCARD;
import static org.joyrest.utils.CollectionUtils.isSingletonList;
import static org.joyrest.utils.CollectionUtils.nonEmpty;

/**
 * Helper utility class for matching an incoming requests against a route configuration
 *
 * @author pbouda
 * @see org.joyrest.processor.RequestProcessor
 */
public final class RequestMatcher {

    private RequestMatcher() {
    }

    /**
     * Matches route produces configuration and Accept-header in an incoming request
     *
     * @param route   route configuration
     * @param request incoming request object
     * @return returns {@code true} if the given route has produces Media-Type one of an Accept from an incoming request
     */
    public static boolean matchProduces(InternalRoute route, InternalRequest<?> request) {
        if (nonEmpty(request.getAccept())) {
            List<MediaType> matchedAcceptTypes = getAcceptedMediaTypes(route.getProduces(), request.getAccept());

            if (nonEmpty(matchedAcceptTypes)) {
                request.setMatchedAccept(matchedAcceptTypes.get(0));
                return true;
            }
        }

        return false;
    }

    private static List<MediaType> getAcceptedMediaTypes(List<MediaType> routeProduces, List<MediaType> requestAccepts) {
        if (isSingletonList(routeProduces) && WILDCARD.equals(routeProduces.get(0)))
            return requestAccepts;

        if (isSingletonList(requestAccepts) && WILDCARD.equals(requestAccepts.get(0)))
            return routeProduces;

        return requestAccepts.stream()
                .filter(routeProduces::contains)
                .collect(toList());
    }

    /**
     * Matches route consumes configuration and Content-Type header in an incoming request
     *
     * @param route   route configuration
     * @param request incoming request object
     * @return returns {@code true} if the given route has consumes Media-Type one of a Content-Type from an incoming request
     */
    public static boolean matchConsumes(InternalRoute route, InternalRequest<?> request) {
        if (route.getConsumes().contains(WILDCARD))
            return true;

        return route.getConsumes().contains(request.getContentType());
    }

    /**
     * Matches route an http method in an incoming request
     *
     * @param route   route configuration
     * @param request incoming request object
     * @return returns {@code true} if the given route has the same http method as an incoming request
     */
    public static boolean matchHttpMethod(InternalRoute route, InternalRequest<?> request) {
        return route.getHttpMethod() == request.getMethod();
    }
}
