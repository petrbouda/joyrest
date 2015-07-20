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
package org.joyrest.ittest.aspect;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;

//@Configuration
public class AspectConfig {

    @Bean
    ControllerConfiguration aspectController() {
        return new AspectController();
    }

    @Bean
    Interceptor firstAspect() {
        return new FirstInterceptor();
    }

    @Bean
    Interceptor secondAspect() {
        return new SecondInterceptor();
    }

    @Bean
    Interceptor thirdAspect() {
        return new ThirdInterceptor();
    }

}
