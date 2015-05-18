package org.joyrest.undertow;

import static io.undertow.Handlers.path;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.joyrest.context.ApplicationContext;
import org.joyrest.undertow.handler.UndertowApplicationHandler;

import io.undertow.Undertow;

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
