package org.joyrest.examples.combiner.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * A simple in-memory store for serializable values based on key.
 * 
 * All instances of this interface and their methods are thread-safe.
 */
public interface InMemoryDataStore {

	/**
	 * Store new data or replace existing data stored under a {@code key} with the new data.
	 * 
	 * The method throws an exception if the {@code key} is {@code null}.
	 * 
	 * @param key a unique identifier of the location where the data should be stored or replaced. Must not be {@code null}.
	 * @param data new data to be stored under the given {@code key}. May be {@code null}, in which case the data will be removed from the
	 *            store.
	 * @return {@code null} if there are no data stored under the given {@code key} or any replaced data previously stored under the
	 *         {@code key}.
	 *
	 * @throws NullPointerException in case the {@code key} is {@code null}.
	 */
	<T extends Serializable> Serializable put(String key, T data);

	/**
	 * Retrieve data from the data store.
	 * 
	 * The method retrieves the data stored under a unique {@code key} from the data store and returns the data cast into the Java type
	 * specified by {@code type} parameter.
	 * 
	 * @param key a unique identifier of the location from which the stored data should be retrieved. Must not be {@code null}.
	 * @param type expected Java type of the data being retrieved. Must not be {@code null}.
	 * @return retrieved data or {@code null} if there are no data stored under the given {@code key}.
	 *
	 * @throws NullPointerException in case the {@code key} or {@code type} is {@code null}.
	 * @throws ClassCastException in case the data stored under the given {@code key} cannot be cast to the Java type represented by
	 *             {@code type} parameter.
	 */
	<T extends Serializable> T get(String key, Class<T> type);

	/**
	 * Save current content of the data store to an output stream.
	 * 
	 * The operation is guaranteed to produce a consistent snapshot of the data store inner state.
	 *
	 * @param out output stream where the content of the data store is saved. The method does not close the stream.
	 *
	 * @throws IOException in case the save operation failed.
	 */
	void save(OutputStream out) throws IOException;

	/**
	 * Load content of the data store from an input stream.
	 *
	 * Any content previously stored in the data store will be discarded and replaced with the content loaded from the input stream.
	 * 
	 * @param in input stream from which the content of the data store is loaded. The method does not close the stream.
	 *
	 * @throws IOException in case the load operation failed.
	 */
	void load(InputStream in) throws IOException;
}