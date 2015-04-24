package org.joyrest.examples.jokeapp.dagger;

import org.joyrest.context.ApplicationContext;
import org.joyrest.dagger.DaggerConfigurer;
import org.joyrest.grizzly.GrizzlyServer;

public class StartDagger {

	public static void main(String... args) throws Exception {
		DaggerConfigurer configurer = new DaggerConfigurer();
		ApplicationContext applicationContext = configurer.initialize(new DaggerApplicationModule());
		GrizzlyServer.start(applicationContext, "/services", 5000);
	}

}
