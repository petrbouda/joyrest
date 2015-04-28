package org.joyrest.perftest.jersey.feeds.binder;

import javax.inject.Singleton;

import org.joyrest.perftest.jersey.feeds.service.FeedService;
import org.joyrest.perftest.jersey.feeds.service.FeedServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FeedServiceImpl.class)
			.to(FeedService.class)
			.in(Singleton.class);
	}

}
