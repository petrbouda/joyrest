package org.joyrest.perftest.jersey.feeds;

import static org.joyrest.perftest.common.GrizzlyServletStart.run;

import org.glassfish.jersey.servlet.ServletContainer;

public class GrizzlyServletStart {

	public static void main(String[] args) {
		run(new ServletContainer(new ApplicationConfig()));
	}
}
