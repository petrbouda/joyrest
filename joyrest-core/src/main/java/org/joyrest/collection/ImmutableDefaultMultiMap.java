package org.joyrest.collection;

import org.joyrest.collection.annotation.Default;

import java.util.List;
import java.util.Optional;

/**
 * Immutable version of {@link org.joyrest.collection.DefaultMultiMapImpl}
 *
 * @see org.joyrest.collection.DefaultMultiMapImpl
 * @author boudap
 */
public class ImmutableDefaultMultiMap<K, V extends Default>
        extends ImmutableMultiMap<K, V> implements DefaultMultiMap<K, V> {

    private final DefaultMultiMap<K, V> datastore;

    ImmutableDefaultMultiMap(DefaultMultiMap<K, V> datastore) {
        super(datastore);
        this.datastore = datastore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> getAll(K key) {
        return datastore.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<V> getDefault(K key) {
        return datastore.getDefault(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> getAllNonDefault(K key) {
        return datastore.getAllNonDefault(key);
    }
}
