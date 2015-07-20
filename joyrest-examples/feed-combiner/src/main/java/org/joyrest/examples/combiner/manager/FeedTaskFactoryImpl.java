/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.examples.combiner.manager;

import java.net.URL;
import java.util.List;
import java.util.function.Function;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.store.InMemoryDataStore;

import com.sun.syndication.feed.synd.SyndEntry;

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
