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

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathRouteConfig {

    @Bean
    ControllerConfiguration pathRouteController1() {
        return new PathRouteController1();
    }

    @Bean
    ControllerConfiguration pathRouteController2() {
        return new PathRouteController2();
    }

    @Bean
    ControllerConfiguration pathRouteController3() {
        return new PathRouteController3();
    }

}
