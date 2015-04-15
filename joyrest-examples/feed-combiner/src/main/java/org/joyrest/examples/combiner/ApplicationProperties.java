package org.joyrest.examples.combiner;

/**
 * Application configuration properties
 *
 * @author Petr Bouda
 **/
public final class ApplicationProperties {

	/**
	 * Used as a default value in task scheduler if the value is not set for the feed during a feed creation. It particularly means, how
	 * often will be feed entries of the given refreshed and downloaded.
	 **/
	public static final String DEFAULT_REFRESH_PERIOD = "feed.default-refresh-period";

	/**
	 * Indicates the number of threads in a pool of the task scheduler which is dedicated to download new entries.
	 **/
	public static final String SCHEDULER_POOL_SIZE = "feed.scheduler.pool-size";

}
