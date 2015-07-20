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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * This class is used to download entries of the defined URL (URL of the feed).
 * <p>
 * The implemented {@link Function#apply(Object)} is annotated with {@link SuppressWarnings} due to an absence of generic types in
 * {@link SyndFeed}
 * </p>
 * <p>
 * The class implements {@link Function} for a purpose to use it in the {@link java.util.stream.Stream}.
 * </p>
 *
 * @author Petr Bouda
 **/
public class FeedDownloader implements Function<URL, List<SyndEntry>> {

    private static Logger LOG = Logger.getLogger(FeedDownloader.class.getName());

    @Override
    @SuppressWarnings("unchecked")
    public List<SyndEntry> apply(URL url) {
        try (XmlReader xmlReader = new XmlReader(url)) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(xmlReader);
            return feed.getEntries();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "An error during downloading and parsing a given feed: " + url, e);
        }
        return Collections.emptyList();
    }
}