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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.oauth2.context;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class JoySecurityContextHolder {

    private static final ThreadLocal<JoySecurityContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static JoySecurityContext getContext() {
        JoySecurityContext ctx = contextHolder.get();

        if (isNull(ctx)) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public static void setContext(JoySecurityContext context) {
        requireNonNull(context, "Security Context cannot be null.");
        contextHolder.set(context);
    }

    private static JoySecurityContext createEmptyContext() {
        return new JoySecurityContextImpl();
    }
}
