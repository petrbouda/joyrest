package org.joyrest.examples.getstarted;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
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

		bind(new JsonReaderWriter())
			.to(Reader.class)
			.to(Writer.class);
	}
}
