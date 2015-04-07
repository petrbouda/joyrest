package org.joyrest.examples.combiner.manager;

import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.store.DataStoreObserver;
import org.joyrest.examples.combiner.store.InMemoryDataStore;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

/**
 * This class is used for purposes of processing and listening a new saved or 
 * removed entities in {@link InMemoryDataStore} and according to 
 * its {@link CombinedFeed#getRefreshPeriod()} property is able to give an entity
 * into the queue for another processing which is dedicated to consumers.
 * 
 * @author Petr Bouda
 * */
public class FeedDataStoreManager implements DataStoreObserver {

	private static final long START_DELAY = 0L;

	private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	
	private final ScheduledExecutorService scheduler;
	
	private final FeedTaskFactory taskFactory;
	
	// Concurrent Map used for purposes of cancellation tasks in the given executor
	private Map<String, ScheduledFuture<?>>  scheduledTasks = new ConcurrentHashMap<>();
	
	public FeedDataStoreManager(FeedTaskFactory taskFactory) {
		this(taskFactory, Executors.newScheduledThreadPool(1));
	}
	
	public FeedDataStoreManager(FeedTaskFactory taskFactory, ScheduledExecutorService scheduler) {
		this.taskFactory = taskFactory;
		this.scheduler = scheduler;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Serializable entity) {
		createTask(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAll(Collection<Serializable> entities) {
		entities.stream().forEach(this::createTask);
	}

	private void createTask(Serializable serializableEntity) {
		if(serializableEntity instanceof CombinedFeed) {
			CombinedFeed feed = (CombinedFeed) serializableEntity;
			ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate
					(taskFactory.get(feed), START_DELAY, feed.getRefreshPeriod(), DEFAULT_TIME_UNIT);
			scheduledTasks.put(feed.getId(), scheduledTask);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(String key) {
		removeTask(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAll(Collection<String> keys) {
		keys.stream().forEach(this::removeTask);
	}

	private void removeTask(String key) {
		ScheduledFuture<?> scheduledTask = scheduledTasks.get(key);
		if(scheduledTask != null){
			scheduledTask.cancel(true);
			scheduledTasks.remove(key);
		}
	}

}
