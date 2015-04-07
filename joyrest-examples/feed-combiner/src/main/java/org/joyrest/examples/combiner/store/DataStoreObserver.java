package org.joyrest.examples.combiner.store;

import java.io.Serializable;
import java.util.Collection;


/**
 * Instances of this interface are able to receive the notification from 
 * {@link org.joyrest.examples.combiner.store.ObservableDataStore}.
 *
 * @author Petr Bouda
 **/
public interface DataStoreObserver {

	/**
	 * Method updates an inner state of an instance of this interface
	 * using a provided saved entity.
	 * 
	 * @param entity newly saved entity
	 **/
	void save(Serializable entity);
	
	/**
	 * Method updates an inner state of an instance of this interface
	 * using a provided collection of saved entities.
	 * 
	 * @param entities list of newly saved entities
	 **/
	void saveAll(Collection<Serializable> entities);
	
	/**
	 * Method updates an inner state of an instance of this interface
	 * using a provided key of the removed entity.
	 * 
	 * @param key removed entity
	 **/
	void remove(String key);
	
	/**
	 * Method updates an inner state of an instance of this interface
	 * using a provided collection of keys of the removed entities.
	 * 
	 * @param keys collection of removed entities
	 **/
	void removeAll(Collection<String> keys);
}
