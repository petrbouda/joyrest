package org.joyrest.examples.jokeapp.dagger;

import org.joyrest.dagger.DaggerConfigurer;
import org.joyrest.examples.jokeapp.*;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.*;

import dagger.Module;
import dagger.Provides;

@Module(injects = { DaggerConfigurer.DaggerConfigurationProvider.class, JokeController.class }, library = true)
public class DaggerApplicationModule {

	private final JsonReaderWriter jsonReaderWriter = new JsonReaderWriter();

	@Provides(type = Provides.Type.SET)
	Writer jsonWriter() {
		return jsonReaderWriter;
	}

	@Provides(type = Provides.Type.SET)
	Reader jsonReader() {
		return jsonReaderWriter;
	}

	@Provides(type = Provides.Type.SET)
	ControllerConfiguration jokeControllerConfiguration() {
		return new JokeController();
	}

	@Provides
	JokeService jokeService() {
		return new JokeServiceImpl();
	}

}
