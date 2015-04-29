package org.joyrest.perftest.joyrest.feeds.service;

import java.util.List;

import org.joyrest.perftest.joyrest.feeds.model.FeedEntry;

public interface FeedService {

	FeedEntry save(FeedEntry feed);

	List<FeedEntry> getAll();

	void ping();
}
