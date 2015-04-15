package org.joyrest.examples.combiner.manager;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.model.CombinedFeed.CombinedFeedBuilder;
import org.joyrest.examples.combiner.model.FeedEntry;
import org.joyrest.examples.combiner.store.InMemoryDataStore;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * An instance of this class implements the {@link Runnable#run() run method} with functionality of downloading all entries from feed which
 * are available. These entries are added into the combined feed and stored in the database again (storing means rewrite previous combined
 * feed by a new version with added entries).
 * 
 * @author Petr Bouda
 **/
public class FeedDownloadTask implements Runnable {

	private static Logger LOG = Logger.getLogger(FeedDownloadTask.class.getName());

	private final FeedEntryMapper entryMapper = new FeedEntryMapper();

	private final Function<URL, List<SyndEntry>> feedDownloader;
	private final InMemoryDataStore datastore;
	private final String combinedFeedID;

	FeedDownloadTask(Function<URL, List<SyndEntry>> feedDownloader, InMemoryDataStore datastore, String feedId) {
		this.combinedFeedID = feedId;
		this.datastore = datastore;
		this.feedDownloader = feedDownloader;
	}

	@Override
	public void run() {
		CombinedFeed fetchedCombinedFeed = datastore.get(combinedFeedID, CombinedFeed.class);
		if (fetchedCombinedFeed == null) {
			LOG.warning("There is no CombinedFeed for the ID: " + combinedFeedID);
			return;
		}

		List<FeedEntry> entries = fetchedCombinedFeed.getUrls().parallelStream()
			.map(feedDownloader)
			.flatMap(syndEntries -> syndEntries.parallelStream())
			.map(entryMapper)
			.filter(Objects::nonNull)
			.sorted((e1, e2) -> e2.getPublishDate().compareTo(e1.getPublishDate()))
			.collect(Collectors.toList());

		CombinedFeed combinedFeed = CombinedFeedBuilder.of(fetchedCombinedFeed).feedEntries(entries).build();

		datastore.put(combinedFeedID, combinedFeed);

		LOG.log(Level.FINEST, "New entries for the CombinedFeed were downloaded [combined-feed={0}, entries-count={1}]",
				new Object[] { combinedFeed.getId(), entries.size() });
	}

	/**
	 * The exception which is used for transform {@link SyndEntry synd entry} to this application-friendly version {@link FeedEntry feed
	 * entry}.
	 * <p>
	 * The class implements {@link Function function interface} for a purpose to use it in the {@link Stream stream}.
	 * </p>
	 * 
	 * @author Petr Bouda
	 **/
	private static class FeedEntryMapper implements Function<SyndEntry, FeedEntry> {

		@Override
		public FeedEntry apply(SyndEntry entry) {
			if (entry.getDescription() == null) {
				return null;
			}

			String title = entry.getTitle();
			String link = entry.getLink();
			String description = entry.getDescription().getValue();
			Date publishedDate = entry.getPublishedDate();
			return new FeedEntry(title, link, description, publishedDate);
		}
	}
}
