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
package org.joyrest.examples.combiner.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.joyrest.examples.combiner.ApplicationProperties;
import org.joyrest.examples.combiner.generator.IdGenerator;
import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.model.CombinedFeed.CombinedFeedBuilder;
import org.joyrest.examples.combiner.store.InMemoryDataStore;
import org.joyrest.examples.combiner.store.ReadWriteLockDataStore;
import org.joyrest.hk2.extension.property.Property;

public class CombinedFeedService implements CrudService<CombinedFeed> {

    @Inject
    private InMemoryDataStore datastore;

    @Inject
    private IdGenerator idGenerator;

    @Property(ApplicationProperties.DEFAULT_REFRESH_PERIOD)
    private Long defaultRefreshPeriod;

    @Override
    public CombinedFeed save(CombinedFeed insertedFeed) {
        String entityId = idGenerator.getId();

        CombinedFeedBuilder feedBuilder = CombinedFeedBuilder.of(insertedFeed).id(entityId).feedEntries(null);
        if (insertedFeed.getRefreshPeriod() == 0) {
            feedBuilder.refreshPeriod(defaultRefreshPeriod);
        }

        CombinedFeed combinedFeed = feedBuilder.build();
        datastore.put(entityId, combinedFeed);
        return combinedFeed;
    }

    @Override
    public Optional<Serializable> delete(String feedId) {
        return Optional.ofNullable(datastore.put(feedId, null));
    }

    @Override
    public CombinedFeed update(CombinedFeed feed) {
        throw new UnsupportedOperationException("This operation is not implemented yet.");
    }

    @Override
    public Optional<CombinedFeed> get(String feedId) {
        return Optional.ofNullable(datastore.get(feedId, CombinedFeed.class));
    }

    @Override
    public List<CombinedFeed> getAll() {
        // TODO ugly, for purposes of CRUD controller
        if (datastore instanceof ReadWriteLockDataStore) {
            ReadWriteLockDataStore rwDatastore = (ReadWriteLockDataStore) datastore;
            Collection<Serializable> entities = rwDatastore.getAll();
            return entities.parallelStream()
                .filter(entity -> entity instanceof CombinedFeed)
                .map(CombinedFeed.class::cast)
                .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
