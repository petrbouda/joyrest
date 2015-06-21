package org.joyrest.examples.nondi.jokeapp;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.NonDiConfigurer;
import org.joyrest.jackson.JacksonReaderWriter;

public class JokeConfigurer extends NonDiConfigurer {

	@Override
	public ApplicationContext initialize() {
		JokeService service = new JokeServiceImpl();
		JokeController jokeController = new JokeController(service);
		JacksonReaderWriter readerWriter = new JacksonReaderWriter();

		addControllerConfiguration(jokeController);
		addReader(readerWriter);
		addWriter(readerWriter);

		return super.initializeContext();
	}
}
