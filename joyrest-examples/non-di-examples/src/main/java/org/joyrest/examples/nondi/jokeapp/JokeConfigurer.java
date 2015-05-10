package org.joyrest.examples.nondi.jokeapp;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.NonDiConfigurer;
import org.joyrest.utils.transform.JsonReaderWriter;

public class JokeConfigurer extends NonDiConfigurer {

	@Override
	public ApplicationContext initialize() {
		JokeService service = new JokeServiceImpl();
		JokeController jokeController = new JokeController(service);
		JsonReaderWriter readerWriter = new JsonReaderWriter();

		addControllerConfiguration(jokeController);
		addReader(readerWriter);
		addWriter(readerWriter);

		return super.initializeContext();
	}
}
