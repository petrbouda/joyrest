package org.joyrest.examples.combiner.manager;


import org.joyrest.examples.combiner.model.CombinedFeed;

/**
 * Factory which creates a new {@link FeedDownloadTask} for purposes of processing.
 *
 * @author Petr Bouda
 */
public interface FeedTaskFactory {

	/**
	 * Creates new instances of {@link FeedDownloadTask}
	 *
	 * @param feed combined feed which provides an information for a purpose of processing
	 * @return new task prepared for processing
	 */
	FeedDownloadTask get(CombinedFeed feed);

}
