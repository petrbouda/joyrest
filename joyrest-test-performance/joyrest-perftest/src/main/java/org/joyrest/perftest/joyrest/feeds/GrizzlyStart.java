package org.joyrest.perftest.joyrest.feeds;

import static org.joyrest.perftest.common.GrizzlyStart.run;

import org.glassfish.grizzly.http.server.HttpServer;
import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.handler.GrizzlyApplicationHandler;
import org.joyrest.hk2.HK2Configurer;
import org.joyrest.perftest.joyrest.feeds.binder.ApplicationBinder;

public class GrizzlyStart {

	public static void main(String[] args) {
		HK2Configurer configurer = new HK2Configurer();
		ApplicationContext applicationContext = configurer.initialize(new ApplicationBinder());

		HttpServer server = HttpServer.createSimpleServer(null, 5000);
		server.getServerConfiguration().addHttpHandler(
				new GrizzlyApplicationHandler(applicationContext), "/services");
		run(server);
	}
}
