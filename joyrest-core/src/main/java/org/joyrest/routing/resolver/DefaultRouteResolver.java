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
package org.joyrest.routing.resolver;

import java.util.List;

import org.joyrest.context.ApplicationContext;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.PathComparator;
import org.joyrest.routing.matcher.RequestMatcher;
import org.joyrest.stream.BiStream;
import static org.joyrest.exception.type.RestException.notAcceptableSupplier;
import static org.joyrest.exception.type.RestException.notFoundSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

public class DefaultRouteResolver implements RouteResolver {

    /* Class which compares path from route and incoming model */
    private final PathComparator pathComparator = new PathComparator();

    /* All routes configures in an application */
    private final List<InternalRoute> routes;

    public DefaultRouteResolver(ApplicationContext context) {
        this.routes = context.getRoutes();
    }

    @Override
    public InternalRoute resolveRoute(InternalRequest<?> request) {
        return BiStream.of(routes.stream(), request)
            .throwIfNull(pathComparator, notFoundSupplier(String.format(
                "There is no route suitable for path [%s]",
                request.getPath())))

            .throwIfNull(RequestMatcher::matchHttpMethod, notFoundSupplier(String.format(
                "There is no route suitable for path [%s], method [%s]",
                request.getPath(), request.getMethod())))

            .throwIfNull(RequestMatcher::matchConsumes, unsupportedMediaTypeSupplier(String.format(
                "There is no route suitable for path [%s], method [%s], content-type [%s]",
                request.getPath(), request.getMethod(), request.getHeader(CONTENT_TYPE).orElse("---"))))

            .throwIfNull(RequestMatcher::matchProduces, notAcceptableSupplier(String.format(
                "There is no route suitable for path [%s], method [%s], accept [%s]",
                request.getPath(), request.getMethod(), request.getHeader(ACCEPT).orElse("---"))))
            .findAny().get();
    }
}
