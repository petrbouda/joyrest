package org.joyrest.perftest.jersey.feeds;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class App {

	private static Logger LOG = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		try {
			// Logging settings
			InputStream loggingStream = App.class.getResourceAsStream("/logging.properties");
			LogManager.getLogManager().readConfiguration(loggingStream);

			URI baseUri = UriBuilder.fromUri("http://localhost/").port(5000).path("services").build();
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, new ApplicationConfig(), false);
			
			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
			
			server.start();
			LOG.info("Feed Combine Application started. Stop the application using ^C.");
			Thread.currentThread().join();
		} catch (IOException | InterruptedException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

}
