package org.joyrest.examples.di.jokeapp.dagger;

import org.joyrest.aspect.Aspect;
import org.joyrest.dagger.DaggerConfigurer;
import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.gson.GsonReaderWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import dagger.Module;
import dagger.Provides;

@Module(injects = { DaggerConfigurer.DaggerConfigurationProvider.class, JokeController.class }, library = true)
public class DaggerApplicationModule {

	private final GsonReaderWriter jsonReaderWriter = new GsonReaderWriter();

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
