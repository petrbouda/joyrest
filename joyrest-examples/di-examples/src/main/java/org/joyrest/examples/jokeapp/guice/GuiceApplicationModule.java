package org.joyrest.examples.jokeapp.guice;

import javax.inject.Singleton;

import org.joyrest.examples.jokeapp.*;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.*;

import com.google.inject.AbstractModule;

public class GuiceApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(JokeService.class)
			.to(JokeServiceImpl.class)
			.in(Singleton.class);

		bind(ControllerConfiguration.class)
			.to(JokeController.class)
			.in(Singleton.class);

		JsonReaderWriter readerWriter = new JsonReaderWriter();

		bind(Reader.class)
			.toInstance(readerWriter);

		bind(Writer.class)
			.toInstance(readerWriter);
	}
}
