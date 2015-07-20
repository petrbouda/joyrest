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
package org.joyrest.examples.combiner.store;

/**
 * A Datastore which implements this interface is able to notify all {@link DataStoreObserver observers} and react to an adding
 * or a
 * removing entities.
 * <p>
 * The order in which notifications will be delivered to multiple observers is not specified.
 * </p>
 *
 * @author Petr Bouda
 **/
public interface ObservableDataStore extends InMemoryDataStore {

    /**
     * Adds an {@link DataStoreObserver observer} to the set of observers for this observable object {@link ObservableDataStore}.
     *
     * @param observer an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    void addObserver(DataStoreObserver observer);

    /**
     * Deletes an {@link DataStoreObserver observer} from the set of observers of this object.
     *
     * Passing {@code null} to this method will have no effect.
     *
     * @param observer the observer to be deleted.
     */
    void deleteObserver(DataStoreObserver observer);

}
