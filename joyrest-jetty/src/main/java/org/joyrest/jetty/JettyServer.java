package org.joyrest.jetty;

import org.eclipse.jetty.server.Server;
import org.joyrest.context.ApplicationContext;
import org.joyrest.jetty.handler.JettyApplicationHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JettyServer {

	private static Logger LOG = Logger.getLogger(JettyServer.class.getName());

	public static void start(final ApplicationContext applicationConfig, String path, final int port) {
				try {
						Server server = new Server(port);
			            // TODO set context-path
							server.setHandler(new JettyApplicationHandler(applicationConfig));
			
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
