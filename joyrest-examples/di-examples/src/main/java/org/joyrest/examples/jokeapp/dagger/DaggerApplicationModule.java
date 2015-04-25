package org.joyrest.examples.jokeapp.dagger;

import org.joyrest.aspect.Aspect;
import org.joyrest.dagger.DaggerConfigurer;
import org.joyrest.examples.jokeapp.JokeController;
import org.joyrest.examples.jokeapp.JokeService;
import org.joyrest.examples.jokeapp.JokeServiceImpl;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.JsonReaderWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import dagger.Module;
import dagger.Provides;

@Module(injects = { DaggerConfigurer.DaggerConfigurationProvider.class, JokeController.class }, library = true)
public class DaggerApplicationModule {

	private final JsonReaderWriter jsonReaderWriter = new JsonReaderWriter();

	/* only for dagger-compiler purposes */
	@Provides(type = Provides.Type.SET)
	Aspect nullAspect() {
		return null;
	}

	/* only for dagger-compiler purposes */
	@Provides(type = Provides.Type.SET)
	ExceptionConfiguration jokeExceptionConfiguration() {
		return null;
	}

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
