package org.joyrest.perftest.jersey.feeds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.joyrest.perftest.jersey.feeds.model.FeedEntry;

public class FeedServiceImpl implements FeedService {

	public FeedEntry save(FeedEntry feed) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return feed;
	}

	public List<FeedEntry> getAll() {
		FeedEntry entry = new FeedEntry();
		entry.setAuthor("Mr. George Somebody");
		entry.setTitle("How to find the best REST Framework");
		entry.setDescription("Description about How to find the best REST Framework");

		List<FeedEntry> entries = new ArrayList<>();
		IntStream.range(1, 50).forEach(n -> entries.add(entry));
		return entries;
	}

	public void ping() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
