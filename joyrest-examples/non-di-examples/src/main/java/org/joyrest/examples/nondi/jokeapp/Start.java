package org.joyrest.examples.nondi.jokeapp;

import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.GrizzlyServer;

public class Start {

	public static void main(String... args) throws Exception {
		JokeConfigurer configurer = new JokeConfigurer();
		ApplicationContext applicationContext = configurer.initialize();
		GrizzlyServer.start(applicationContext, 5000, "/services");
	}

}
