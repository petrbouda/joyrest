package org.joyrest.perftest.joyrest.feeds.binder;

import javax.inject.Singleton;

import org.joyrest.perftest.joyrest.feeds.resources.FeedController;
import org.joyrest.perftest.joyrest.feeds.service.FeedService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.joyrest.perftest.joyrest.feeds.service.FeedServiceImpl;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.JsonReaderWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(JsonReaderWriter.class)
			.to(Writer.class)
			.to(Reader.class)
			.in(Singleton.class);

		bind(FeedServiceImpl.class)
			.to(FeedService.class)
			.in(Singleton.class);

		bind(FeedController.class)
			.to(ControllerConfiguration.class)
			.in(Singleton.class);
	}

}
