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
package org.joyrest.ittest.oauth.services;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.joyrest.ittest.oauth.model.Feed;

import static java.util.stream.Collectors.toList;

public class FeedServiceImpl implements FeedService {

    @Override
    public Feed save(Feed feed) {
        return new Feed(
            feed.getTitle() + " - " + 1,
            feed.getDescription() + " - " + 1,
            new Date());
    }

    @Override
    public Feed get(String feedId) {
        return new Feed("JoyRest Feed - " + feedId, "JoyRest Feed Description - " + feedId, new Date());
    }

    @Override
    public List<Feed> getAll() {
        return IntStream.range(1, 25)
            .mapToObj(i -> new Feed("JoyRest Feed - " + i, "JoyRest Feed Description - " + i, new Date()))
            .collect(toList());
    }
}
