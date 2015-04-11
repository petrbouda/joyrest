package org.joyrest.examples.getstarted;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.*;

import javax.inject.Singleton;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(JokeServiceImpl.class)
			.to(new TypeLiteral<JokeService>() {
			})
			.in(Singleton.class);

		bind(JokeController.class)
			.to(ControllerConfiguration.class)
			.in(Singleton.class);

		bind(new JsonReader())
			.to(Reader.class);

		bind(new JsonWriter())
			.to(Writer.class);
	}
}
