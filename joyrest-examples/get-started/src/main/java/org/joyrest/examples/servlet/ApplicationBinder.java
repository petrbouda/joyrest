package org.joyrest.examples.servlet;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.JsonReader;
import org.joyrest.transform.JsonWriter;
import org.joyrest.transform.ReaderRegistrar;
import org.joyrest.transform.WriterRegistrar;

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

		bind(new ReaderRegistrar(new JsonReader(), MediaType.JSON))
			.to(ReaderRegistrar.class);

		bind(new WriterRegistrar(new JsonWriter(), MediaType.JSON))
			.to(WriterRegistrar.class);
	}
}
