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
package org.joyrest.jetty;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.joyrest.context.ApplicationContext;
import org.joyrest.jetty.handler.JettyApplicationHandler;

public class JettyServer {

    private static Logger LOG = Logger.getLogger(JettyServer.class.getName());

    public static void start(final ApplicationContext applicationContext, final int port, String path) {
        try {
            Server server = new Server(port);
            ContextHandler contextHandler = new ContextHandler();
            contextHandler.setContextPath(path);
            contextHandler.setHandler(new JettyApplicationHandler(applicationContext));

            server.setHandler(contextHandler);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    server.stop();
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, null, e);
                }
            }));
            server.start();

            LOG.info("Jetty Server started. Stop the application using ^C.");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
