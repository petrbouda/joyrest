package org.joyrest.examples.di.jokeapp.hk2;

import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.hk2.HK2Configurer;

public class StartHK2 {

	public static void main(String... args) throws Exception {
		HK2Configurer configurer = new HK2Configurer();
		ApplicationContext applicationContext = configurer.initialize(new ApplicationBinder());
		GrizzlyServer.start(applicationContext, 5000, "/services");
	}

}
