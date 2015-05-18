package org.joyrest.jetty;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.joyrest.context.ApplicationContext;
import org.joyrest.jetty.handler.JettyApplicationHandler;

public class JettyServer {

	private static Logger LOG = Logger.getLogger(JettyServer.class.getName());

	public static void start(final ApplicationContext applicationContext, String path, final int port) {
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
