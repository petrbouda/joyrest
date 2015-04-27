package org.joyrest.undertow;

import java.util.logging.*;

import org.joyrest.context.ApplicationContext;
import org.joyrest.undertow.handler.UndertowApplicationHandler;

import io.undertow.Undertow;

public class UndertowServer {

	private static Logger LOG = Logger.getLogger(UndertowServer.class.getName());

	public static void start(final ApplicationContext applicationConfig, final int port) {
		start(applicationConfig, "localhost", port);
	}

	public static void start(final ApplicationContext applicationConfig, String host, final int port) {
		try {
			Undertow server = Undertow.builder()
				.addHttpListener(port, host)
				.setHandler(new UndertowApplicationHandler(applicationConfig))
				.build();
			server.start();

			LOG.info("Undertow Server started. Stop the application using ^C.");
			Thread.currentThread().join();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}
}
