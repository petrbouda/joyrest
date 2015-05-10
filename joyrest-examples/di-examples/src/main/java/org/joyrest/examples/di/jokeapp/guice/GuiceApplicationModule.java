package org.joyrest.examples.di.jokeapp.guice;


import static com.google.inject.multibindings.Multibinder.newSetBinder;

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.utils.transform.JsonReaderWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

public class GuiceApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<ControllerConfiguration> controllerBinder = newSetBinder(binder(), ControllerConfiguration.class);
		controllerBinder.addBinding().to(JokeController.class).in(Singleton.class);

		JsonReaderWriter readerWriter = new JsonReaderWriter();
		Multibinder<Writer> writerBinder = newSetBinder(binder(), Writer.class);
		writerBinder.addBinding().toInstance(readerWriter);

		Multibinder<Reader> readerBinder = newSetBinder(binder(), Reader.class);
		readerBinder.addBinding().toInstance(readerWriter);

		bind(JokeService.class)
			.to(JokeServiceImpl.class)
			.in(Singleton.class);
	}
}
