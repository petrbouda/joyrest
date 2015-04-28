package org.joyrest.perftest.jersey.feeds.service;

import java.util.List;

import org.joyrest.perftest.jersey.feeds.model.FeedEntry;

public interface FeedService {

	FeedEntry save(FeedEntry feed);

	List<FeedEntry> getAll();

	void ping();
}
