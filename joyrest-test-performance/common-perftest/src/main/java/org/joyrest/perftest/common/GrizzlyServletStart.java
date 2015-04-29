package org.joyrest.perftest.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

public class GrizzlyServletStart {

	private static Logger LOG = Logger.getLogger(GrizzlyStart.class.getName());

	public static void run(Servlet servlet) {
		try {
			WebappContext webappContext = new WebappContext("Feeds Application", "");

			ServletRegistration servletRegistration = webappContext.addServlet("FeedServlet", servlet);
			servletRegistration.addMapping("/services/*");

			HttpServer server = HttpServer.createSimpleServer(null, 5000);
			webappContext.deploy(server);
			server.start();
			LOG.info("Feed Combine Application started. Stop the application using ^C.");
			Thread.currentThread().join();
		} catch (IOException | InterruptedException e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}
}
