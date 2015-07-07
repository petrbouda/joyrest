package org.joyrest.examples.combiner.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Instances of this interface have CRUD operation for saving generic entities into the
 * {@link org.joyrest.examples.combiner.store.InMemoryDataStore}.
 *
 * @see org.joyrest.examples.combiner.store.InMemoryDataStore
 * @author Petr Bouda
 **/
public interface CrudService<T extends Serializable> {

    /**
     *
     * @param feed entity for saving
     * @return returns saved entity
     */
    T save(T feed);

    /**
     *
     * @param feed entity which replace the previous one
     * @return updated entity
     */
    T update(T feed);

    /**
     *
     * @param feedId id of the entity which should be deleted
     * @return deleted entity
     */
    Optional<Serializable> delete(String feedId);

    /**
     *
     * @param feedId id of the entity which should be restored
     * @return restored entity
     */
    Optional<T> get(String feedId);

    /**
     *
     * @return all entity which a storage contains
     */
    List<T> getAll();
}
