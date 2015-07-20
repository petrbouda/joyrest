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
package org.joyrest.ittest.path;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.routing.TypedControllerConfiguration;

public class PathRouteController1 extends TypedControllerConfiguration {

    @Override
    protected void configure() {
        setControllerPath("/ittest/path");

        post((req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

        post("path0", (req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

        post("path1/", (req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

        post("/path2/", (req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

        post("/path3", (req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

        post("/path1/path2/", (req, resp) -> {
            resp.status(HttpStatus.NO_CONTENT);
        });

    }
}
