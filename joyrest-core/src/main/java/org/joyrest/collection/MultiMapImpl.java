package org.joyrest.collection;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
public class MultiMapImpl<K, V> implements MultiMap<K, V> {

    private static final String ONLY_NON_NULL_KEYS = "Only non-null keys in MultiMap are allowed";
    private static final String ONLY_NON_NULL_VALUES = "Only non-null values in MultiMap are allowed";

    private final Map<K, List<V>> datastore;

    MultiMapImpl(Map<K, List<V>> datastore) {
        this.datastore = datastore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(K key, V value) {
        requireNonNull(key, ONLY_NON_NULL_KEYS);
        requireNonNull(value, ONLY_NON_NULL_VALUES);

        List<V> list = datastore.get(key);

        if(list != null) {
            list.add(value);
        } else {
            datastore.put(key, Collections.singletonList(value));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(K key, List<V> values) {
        requireNonNull(key, ONLY_NON_NULL_KEYS);
        values.stream().forEach(
                value -> requireNonNull(value, ONLY_NON_NULL_VALUES));

        List<V> list = datastore.get(key);

        if(list != null) {
            list.addAll(values);
        } else {
            datastore.put(key, values);
        }
    }

    /**
     *
     * {@inheritDoc}
     * @throws java.lang.NullPointerException if {@code key} or any {@code value} is null
     */
    @Override
    public List<V> put(K key, List<V> value) {
        requireNonNull(key, ONLY_NON_NULL_KEYS);
        requireNonNull(value, ONLY_NON_NULL_VALUES);

        return datastore.put(key, value);
    }

    /**
     * {@inheritDoc}
     * @throws java.lang.NullPointerException if any {@code key} or any {@code value} is null
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        m.entrySet().stream()
            .peek(entry ->  requireNonNull(entry.getKey(), ONLY_NON_NULL_KEYS))
            .flatMap(entry -> entry.getValue().stream())
            .forEach(value -> requireNonNull(value, ONLY_NON_NULL_VALUES));

        datastore.putAll(m);
    }

    @Override
    public List<V> get(Object key) {
        return datastore.get(key);
    }

    @Override
    public List<V> remove(Object key) {
        return datastore.remove(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return datastore.values().stream()
                .anyMatch(values -> values.contains(value));
    }

    @Override
    public boolean containsKey(Object key) {
        return datastore.containsKey(key);
    }

    @Override
    public void clear() {
        datastore.clear();
    }

    @Override
    public Set<K> keySet() {
        return datastore.keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return datastore.values();
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return datastore.entrySet();
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
