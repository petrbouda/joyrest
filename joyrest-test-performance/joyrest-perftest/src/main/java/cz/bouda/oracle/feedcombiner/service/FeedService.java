package cz.bouda.oracle.feedcombiner.service;

import java.util.List;

import cz.bouda.oracle.feedcombiner.model.FeedEntry;

public interface FeedService {

	FeedEntry save(FeedEntry feed);

	List<FeedEntry> getAll();

	void ping();
}
