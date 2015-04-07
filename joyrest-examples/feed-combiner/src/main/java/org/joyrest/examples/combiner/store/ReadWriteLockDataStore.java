package org.joyrest.examples.combiner.store;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple in-memory store for serializable values based on key. Implementation
 * of the {@link org.joyrest.examples.combiner.store.InMemoryDataStore data
 * store} and their methods are thread-safe.
 * 
 * The data store provides the capability of an adding
 * {@link DataStoreObserver observers} which
 * are able to listen any changes in the data store.
 * 
 * @author Petr Bouda
 */
public class ReadWriteLockDataStore implements InMemoryDataStore, ObservableDataStore {

	private static Logger LOG = Logger.getLogger(ReadWriteLockDataStore.class.getName());

	private HashMap<String, Serializable> datastore = new HashMap<>();

	// check whether is possible to cast a loaded entity to the datastore type
	private static final Predicate<Object> DATASTORE_CAST_CHECKER = new DataStoreCastChecker();
	
	// observers which get information about saving and removing entities
	private Set<DataStoreObserver> observers = new CopyOnWriteArraySet<DataStoreObserver>();

	// instances ensure thread-safe nature of this data store
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readerLock = lock.readLock();
	private final Lock writerLock = lock.writeLock();

	/**
	 * {@inheritDoc}
	 */
	public <T extends Serializable> Serializable put(String key, T data) {
		writerLock.lock();
		try {
			if (key == null) {
				throw new NullPointerException("The parameter 'key' must not be null");
			}
			
			if (data == null) {
				notifyRemove(key);
				return datastore.remove(key);
			}
			
			Serializable previousEntity = datastore.put(key, data);
			if(previousEntity == null){
				notifySave(data);
			}
			return previousEntity;
		} finally {
			writerLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T extends Serializable> T get(String key, Class<T> type) {
		if (key == null) {
			throw new NullPointerException("The parameter 'key' must not be null");
		}

		if (type == null) {
			throw new NullPointerException("The parameter 'type' must not be null");
		}

		readerLock.lock();
		try {
			Serializable object = datastore.get(key);
			return type.cast(object);
		} finally {
			readerLock.unlock();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void save(OutputStream out) throws IOException {
		readerLock.lock();
		try {
			serialize(datastore, out);
		} finally {
			readerLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void load(InputStream in) throws IOException {
		Optional<Object> newDatastore = ReadWriteLockDataStore.deserialize(in);
		
		newDatastore
			.filter(DATASTORE_CAST_CHECKER)
			.map(HashMap.class::cast)
			.ifPresent(loadedStore -> {
				writerLock.lock();
				try {
					notifyRemoveAll(this.datastore.keySet());
					this.datastore = loadedStore;
					notifySaveAll(loadedStore.values());
				} finally {
					writerLock.unlock();
				}
			}
		);
	}

	/**
	 * Deserializes an {@code Optional<Object>} from the given stream.
	 * 
	 * The given stream will not be close.
	 * 
	 * @param input the serialized object input stream, must not be null
	 * @return the serialized object
	 * @throws IOException in case the load operation failed
	 **/
	private static Optional<Object> deserialize(InputStream input) throws IOException {
		try {
			BufferedInputStream bis = new BufferedInputStream(input);
			ObjectInputStream ois = new ObjectInputStream(bis);
			
			return Optional.of(ois.readObject());
		} catch (ClassNotFoundException ex) {
			LOG.log(Level.SEVERE, "An error occurred during deserialization an object.", ex);
		}
		return Optional.empty();
	}

	/**
	 * Serializes an {@code Serializable} to the specified stream.
	 * 
	 * The given stream will not be close.
	 * 
	 * @param object the object to serialize to bytes
	 * @param output the stream to write to, must not be null
	 * @throws IOException in case the load operation failed
	 */
	private static void serialize(Serializable object, OutputStream output) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(output);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		bos.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObserver(DataStoreObserver observer) {
		if (observer == null) {
			throw new NullPointerException("It's not possible to add 'null' observer.");
		}
		observers.add(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteObserver(DataStoreObserver observer) {
		observers.remove(observer);
	}

	public Collection<Serializable> getAll() {
		readerLock.lock();
		try {
			return datastore.values();
		} finally {
			readerLock.unlock();
		}
	}

	private void notifyRemove(String key) {
		for (DataStoreObserver observer : observers) {
			observer.remove(key);
		}
	}

	private void notifyRemoveAll(Collection<String> keys) {
		for (DataStoreObserver observer : observers) {
			observer.removeAll(keys);
		}
	}
	
	private void notifySave(Serializable entity) {
		for (DataStoreObserver observer : observers) {
			observer.save(entity);
		}
	}
	
	private void notifySaveAll(Collection<Serializable> entities) {
		for (DataStoreObserver observer : observers) {
			observer.saveAll(entities);
		}
	}
	
	
	/**
	 * The instance of this class checks whether is possible to cast an unknown loaded entity
	 * to the datastore type.
	 *
	 * @author Petr Bouda
	 **/
	private static final class DataStoreCastChecker implements Predicate<Object> {

		@Override
		public boolean test(Object entity) {
			try {
				@SuppressWarnings({ "unchecked", "unused" })
				HashMap<String, Serializable> loadedStore = (HashMap<String, Serializable>) entity;
			} catch(ClassCastException ex) {
				LOG.log(Level.SEVERE, "The loaded object is not a valid type of the datastore.", ex);
				return false;
			}
			return true;
		}
	}
}
