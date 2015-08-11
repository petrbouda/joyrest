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
package org.joyrest.interceptor;

public class InterceptorInternalOrders {

    public static final int AUTHENTICATION = 50;

    public static final int AUTHORIZATION = 100;

    public static final int SERIALIZATION = 150;

    public static final int PATH_PARAM_PROCESSING = 200;

    public static final int VALIDATION = 250;

}
