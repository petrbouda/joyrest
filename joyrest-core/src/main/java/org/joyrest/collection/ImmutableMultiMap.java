package org.joyrest.collection;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Immutable version of {@link org.joyrest.collection.MultiMapImpl}
 *
 * @see org.joyrest.collection.MultiMapImpl
 * @author boudap
 */
public class ImmutableMultiMap<K,V> implements MultiMap<K, V> {

    public static final String IMMUTABLE_MULTI_MAP = "Immutable MultiMap";

    private final MultiMap<K, V> datastore;

    ImmutableMultiMap(MultiMap<K, V> datastore) {
        requireNonNull(datastore, "MultiMap cannot be null.");
        this.datastore = datastore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(K key, V value) {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(K key, List<V> values) {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    @Override
    public List<V> put(K key, List<V> value) {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    @Override
    public List<V> get(Object key) {
        return datastore.get(key);
    }

    @Override
    public List<V> remove(Object key) {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    @Override
    public boolean containsValue(Object value) {
        return datastore.containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return datastore.containsKey(key);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(IMMUTABLE_MULTI_MAP);
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(datastore.keySet());
    }

    @Override
    public Collection<List<V>> values() {
        return Collections.unmodifiableCollection(datastore.values());
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return Collections.unmodifiableSet(datastore.entrySet());
    }

    @Override
    public int size() {
        return datastore.size();
    }

    @Override
    public boolean isEmpty() {
        return datastore.isEmpty();
    }
}
