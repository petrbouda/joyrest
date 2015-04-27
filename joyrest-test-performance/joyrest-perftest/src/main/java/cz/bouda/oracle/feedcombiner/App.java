package cz.bouda.oracle.feedcombiner;

import org.glassfish.grizzly.http.server.HttpServer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.grizzly.handler.GrizzlyApplicationHandler;
import org.joyrest.hk2.HK2Configurer;

import cz.bouda.oracle.feedcombiner.binder.ApplicationBinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

	private static Logger LOG = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		HK2Configurer configurer = new HK2Configurer();
		ApplicationContext applicationContext = configurer.initialize(new ApplicationBinder());

		try {
			HttpServer server = HttpServer.createSimpleServer(null, 5000);
			server.getServerConfiguration().addHttpHandler(
				new GrizzlyApplicationHandler(applicationContext), "/services");

			Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

			server.start();
			LOG.info("Feed Combine Application started. Stop the application using ^C.");
			Thread.currentThread().join();
		} catch (IOException | InterruptedException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

}
