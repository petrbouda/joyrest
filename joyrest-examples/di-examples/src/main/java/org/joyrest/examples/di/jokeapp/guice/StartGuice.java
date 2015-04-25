package org.joyrest.examples.di.jokeapp.guice;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.Configurer;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.guice.GuiceConfigurer;

public class StartGuice {

	public static void main(String... args) throws Exception {
		Configurer configurer = new GuiceConfigurer();
		ApplicationContext applicationContext = configurer.initialize(new GuiceApplicationModule());
		GrizzlyServer.start(applicationContext, "/services", 5000);
	}

}
