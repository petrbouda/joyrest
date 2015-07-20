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
package org.joyrest.extractor;

import java.util.function.BiFunction;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.PathParam;

public class PathParamExtractor implements BiFunction<RoutePart<?>, String, PathParam> {

    /**
     * Compares the route part (part which is configured) and the path part (part which is gained from the client) and tries to
     * create a
     * path param.
     *
     * <p>
     * If it is just string path, so this method will return @{code null}.
     * </p>
     * <p>
     * If it is param path, so method will find out whether is possible to cast the object or not and then can throw an
     * validation exception
     * </p>
     *
     * @param routePart configured part
     * @param pathPart path from a client's call
     * @return path param derived from route and incoming call or @{code null} whether the path part is
     * @throws RestException is not possible to cast the param type
     **/
    @Override
    public PathParam apply(RoutePart<?> routePart, String pathPart) {
        if (routePart.getType() == RoutePart.Type.PARAM) {
            return new PathParam(routePart.getValue(), pathPart);
        }

        return null;
    }

}
