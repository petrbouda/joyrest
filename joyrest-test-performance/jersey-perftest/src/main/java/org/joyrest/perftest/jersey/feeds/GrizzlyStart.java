package org.joyrest.perftest.jersey.feeds;

import static org.joyrest.perftest.common.GrizzlyStart.run;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class GrizzlyStart {

	public static void main(String[] args) {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(5000).path("services").build();
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, new ApplicationConfig(), false);
		run(server);
	}
}
