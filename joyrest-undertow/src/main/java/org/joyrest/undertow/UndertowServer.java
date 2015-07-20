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
package org.joyrest.undertow;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.joyrest.context.ApplicationContext;
import org.joyrest.undertow.handler.UndertowApplicationHandler;

import io.undertow.Undertow;

import static io.undertow.Handlers.path;

public class UndertowServer {

    private static Logger LOG = Logger.getLogger(UndertowServer.class.getName());

    public static void start(final ApplicationContext applicationConfig, final int port) {
        start(applicationConfig, port, "/");
    }

    public static void start(final ApplicationContext applicationConfig, final int port, final String path) {
        start(applicationConfig, port, path, "localhost");
    }

    public static void start(final ApplicationContext applicationConfig, final int port, final String path, final String host) {
        try {
            Undertow server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(
                    path().addPrefixPath(path, new UndertowApplicationHandler(applicationConfig)))
                .build();
            server.start();

            LOG.info("Undertow Server started. Stop the application using ^C.");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
