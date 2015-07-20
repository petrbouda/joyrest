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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.store.DataStoreObserver;
import org.joyrest.examples.combiner.store.InMemoryDataStore;

/**
 * This class is used for purposes of processing and listening a new saved or removed entities in {@link InMemoryDataStore} and
 * according to
 * its {@link CombinedFeed#getRefreshPeriod()} property is able to give an entity into the queue for another processing which
 * is dedicated
 * to consumers.
 *
 * @author Petr Bouda
 * */
public class FeedDataStoreManager implements DataStoreObserver {

    private static final long START_DELAY = 0L;

    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private final ScheduledExecutorService scheduler;

    private final FeedTaskFactory taskFactory;

    // Concurrent Map used for purposes of cancellation tasks in the given executor
    private Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public FeedDataStoreManager(FeedTaskFactory taskFactory) {
        this(taskFactory, Executors.newScheduledThreadPool(1));
    }

    public FeedDataStoreManager(FeedTaskFactory taskFactory, ScheduledExecutorService scheduler) {
        this.taskFactory = taskFactory;
        this.scheduler = scheduler;
    }

    @Override
    public void save(Serializable entity) {
        createTask(entity);
    }

    @Override
    public void saveAll(Collection<Serializable> entities) {
        entities.forEach(this::createTask);
    }

    private void createTask(Serializable serializableEntity) {
        if (serializableEntity instanceof CombinedFeed) {
            CombinedFeed feed = (CombinedFeed) serializableEntity;
            ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate
                (taskFactory.get(feed), START_DELAY, feed.getRefreshPeriod(), DEFAULT_TIME_UNIT);
            scheduledTasks.put(feed.getId(), scheduledTask);
        }
    }

    @Override
    public void remove(String key) {
        removeTask(key);
    }

    @Override
    public void removeAll(Collection<String> keys) {
        keys.forEach(this::removeTask);
    }

    private void removeTask(String key) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(key);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduledTasks.remove(key);
        }
    }

}
