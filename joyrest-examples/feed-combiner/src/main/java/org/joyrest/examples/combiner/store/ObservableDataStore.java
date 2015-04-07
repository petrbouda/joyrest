package org.joyrest.examples.combiner.store;

/**
 * A Datastore which implements this interface is able to notify all
 * {@link DataStoreObserver observers} and react to an adding or a removing 
 * entities.
 * <p>
 * The order in which notifications will be delivered to multiple
 * observers is not specified.
 * </p>
 * @author Petr Bouda
 **/
public interface ObservableDataStore extends InMemoryDataStore {

	 /**
     * Adds an {@link DataStoreObserver observer}
     * to the set of observers for this observable object 
     * {@link ObservableDataStore}.
     * 
     * @param observer an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
    void addObserver(DataStoreObserver observer);

    /**
     * Deletes an {@link DataStoreObserver observer}
     * from the set of observers of this object.
     * 
     * Passing {@code null} to this method will have no effect.
     * @param observer the observer to be deleted.
     */
    void deleteObserver(DataStoreObserver observer);
    
}
