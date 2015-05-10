package org.joyrest.examples.combiner.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class CombinedFeed implements Serializable {

	private static final long serialVersionUID = 2176919801739913480L;
	private static final Logger LOG = Logger.getLogger(CombinedFeed.class.getName());

	private String id;

	// UnmodifiableList
	@NotNull(message = "At least one valid URL must be in the model.")
	private List<URL> urls;

	@Min(value = 0, message = "Refresh period must be equal or higher than 0.")
	private long refreshPeriod;

	// UnmodifiableList
	private List<FeedEntry> feedEntries;

	@NotNull(message = "Title may not be null.")
	private String title;

	@NotNull(message = "Description may not be null.")
	private String description;

	// for purposes of JAXB
	private CombinedFeed() {
	}

	private CombinedFeed(String id, List<URL> urls, long refreshPeriod,
			List<FeedEntry> feedEntries, String title, String description) {
		this.id = id;
		this.urls = urls;
		this.refreshPeriod = refreshPeriod;
		this.feedEntries = feedEntries;
		this.title = title;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public long getRefreshPeriod() {
		return refreshPeriod;
	}

	public List<URL> getUrls() {
		if (urls == null) {
			return Collections.unmodifiableList(Collections.emptyList());
		}
		return urls;
	}

	public List<FeedEntry> getFeedEntries() {
		if (feedEntries == null) {
			return Collections.unmodifiableList(Collections.emptyList());
		}
		return feedEntries;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("urls", urls)
			.add("refreshPeriod", refreshPeriod)
			.add("feedEntries", feedEntries)
			.add("title", title)
			.add("description", description)
			.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CombinedFeed feed = (CombinedFeed) obj;

		return Objects.equals(this.id, feed.id)
				&& Objects.equals(this.title, feed.title)
				&& Objects.equals(this.description, feed.description)
				&& Objects.equals(this.refreshPeriod, feed.refreshPeriod)
				&& Objects.deepEquals(this.urls, feed.urls)
				&& Objects.deepEquals(this.feedEntries, feed.feedEntries);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, refreshPeriod, urls, feedEntries);
	}

	public static class CombinedFeedBuilder {

		private static final Function<String, URL> mapper = new UrlMapper();

		private String id;
		private List<URL> urls = Collections.unmodifiableList(new ArrayList<>());
		private String title;
		private String description;
		private long refreshPeriod;
		private List<FeedEntry> feedEntries = Collections.unmodifiableList(new ArrayList<>());

		public CombinedFeedBuilder(String id, String... urls) {
			this(id, createUrlList(urls));
		}

		public CombinedFeedBuilder(String id, List<URL> urls) {
			this.id = id;
			this.urls = Collections.unmodifiableList(urls);
		}

		public static CombinedFeedBuilder of(CombinedFeed feed) {
			CombinedFeedBuilder builder = new CombinedFeedBuilder(feed.getId(), feed.getUrls());
			builder.title = feed.getTitle();
			builder.description = feed.getDescription();
			builder.refreshPeriod = feed.getRefreshPeriod();
			builder.feedEntries = feed.getFeedEntries();
			return builder;
		}

		private static List<URL> createUrlList(String... urls) {
			return Arrays.stream(urls)
				.map(mapper)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		}

		public CombinedFeedBuilder id(String id) {
			this.id = id;
			return this;
		}

		public CombinedFeedBuilder urls(List<URL> urls) {
			if (urls == null) {
				this.urls = Collections.unmodifiableList(new ArrayList<>());
			} else {
				this.urls = Collections.unmodifiableList(urls);
			}
			return this;

		}

		public CombinedFeedBuilder title(String title) {
			this.title = title;
			return this;
		}

		public CombinedFeedBuilder description(String description) {
			this.description = description;
			return this;
		}

		public CombinedFeedBuilder refreshPeriod(long refreshPeriod) {
			this.refreshPeriod = refreshPeriod;
			return this;
		}

		public CombinedFeedBuilder feedEntries(List<FeedEntry> feedEntries) {
			if (feedEntries == null) {
				this.feedEntries = Collections.unmodifiableList(new ArrayList<>());
			} else {
				this.feedEntries = Collections.unmodifiableList(feedEntries);
			}
			return this;
		}

		public CombinedFeed build() {
			return new CombinedFeed(id, urls, refreshPeriod, feedEntries, title, description);
		}
	}

	private static final class UrlMapper implements Function<String, URL> {

		@Override
		public URL apply(String url) {
			try {
				return new URL(url);
			} catch (MalformedURLException mue) {
				LOG.log(Level.WARNING, null, mue);
			}
			return null;
		}
	}
}
