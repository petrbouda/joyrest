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
package org.joyrest.grizzly;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;

import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.handler.GrizzlyApplicationHandler;

import static java.util.Objects.nonNull;

public class GrizzlyServer {

    private static Logger LOG = Logger.getLogger(GrizzlyServer.class.getName());

    public static void start(final ApplicationContext applicationConfig, final int port, String path) {
        try {
            // Logging settings
            InputStream loggingStream = GrizzlyServer.class.getResourceAsStream("/logging.properties");
            if (nonNull(loggingStream)) {
                LogManager.getLogManager().readConfiguration(loggingStream);
            }

            HttpServer server = HttpServer.createSimpleServer(null, port);
            server.getServerConfiguration().addHttpHandler(
                new GrizzlyApplicationHandler(applicationConfig), path);

            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

            server.start();
            LOG.info("Grizzly Server started. Stop the application using ^C.");
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

}
