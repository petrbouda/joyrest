package org.joyrest.perftest.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class GrizzlyStart {

	private static Logger LOG = Logger.getLogger(GrizzlyStart.class.getName());

	public static void run(HttpServer server) {
		try {
			// Logging settings
			InputStream loggingStream = GrizzlyStart.class.getResourceAsStream("/logging.properties");
			LogManager.getLogManager().readConfiguration(loggingStream);
			
			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
			
			server.start();
			LOG.info("Feed Combine Application started. Stop the application using ^C.");
			Thread.currentThread().join();
		} catch (IOException | InterruptedException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

}
