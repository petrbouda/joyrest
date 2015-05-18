package org.joyrest.examples.di.jokeapp.dagger;

import org.joyrest.context.ApplicationContext;
import org.joyrest.dagger.DaggerConfigurer;
import org.joyrest.grizzly.GrizzlyServer;

public class StartDagger {

	public static void main(String... args) throws Exception {
		DaggerConfigurer configurer = new DaggerConfigurer();
		ApplicationContext applicationContext = configurer.initialize(new DaggerApplicationModule());
		GrizzlyServer.start(applicationContext, 5000, "/services");
	}

}
