package org.joyrest.examples.combiner.manager;

import com.sun.syndication.feed.synd.SyndEntry;
import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.store.InMemoryDataStore;

import java.net.URL;
import java.util.List;
import java.util.function.Function;

/**
 * Factory which creates a new {@link FeedDownloadTask} for purposes of processing.
 * 
 * @author Petr Bouda
 **/
public class FeedTaskFactoryImpl implements FeedTaskFactory {

	private final Function<URL, List<SyndEntry>> feedDownloader;
	
	private final InMemoryDataStore datastore;
	
	public FeedTaskFactoryImpl(InMemoryDataStore datastore) {
		this(new FeedDownloader(), datastore);
	}
	
	public FeedTaskFactoryImpl(Function<URL, List<SyndEntry>> feedDownloader, InMemoryDataStore datastore) {
		this.feedDownloader = feedDownloader;
		this.datastore = datastore;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FeedDownloadTask get(CombinedFeed feed) {
		return new FeedDownloadTask(feedDownloader, datastore, feed.getId());
	}

}
