package cz.bouda.oracle.feedcombiner.binder;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import cz.bouda.oracle.feedcombiner.service.FeedService;
import cz.bouda.oracle.feedcombiner.service.FeedServiceImpl;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FeedServiceImpl.class)
			.to(FeedService.class)
			.in(Singleton.class);
	}

}
