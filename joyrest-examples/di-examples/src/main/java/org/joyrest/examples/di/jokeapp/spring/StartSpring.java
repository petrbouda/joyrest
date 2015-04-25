package org.joyrest.examples.di.jokeapp.spring;

import org.joyrest.context.ApplicationContext;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.spring.SpringJavaConfigurer;

public class StartSpring {

	public static void main(String... args) throws Exception {
		SpringJavaConfigurer configurer = new SpringJavaConfigurer();
		ApplicationContext applicationContext = configurer.initialize(new ApplicationConfiguration());
		GrizzlyServer.start(applicationContext, "/services", 5000);
	}

}
