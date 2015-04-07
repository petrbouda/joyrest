package org.joyrest.collection;

import java.util.List;
import java.util.Map;

/**
 * Class provides the possibility to save more {@code values} to one {@code key}
 *
 * @see org.joyrest.collection.JoyCollections
 * @see org.joyrest.collection.MultiMapImpl
 * @see org.joyrest.collection.ImmutableMultiMap
 * @param <K> key
 * @param <V> values
 * @author boudap
 */
public interface MultiMap<K, V> extends Map<K, List<V>> {

    /**
     * Adds {@code value} to the existing {@code key}. If the {@code key} does not exist it will be created.
     *
     * @param key
     * @param value
     * @throws java.lang.NullPointerException if {@code key} or {@code value} is null
     */
    void add(K key, V value);

    /**
     * Adds {@code values} to the existing {@code key}. If the {@code key} does not exist it will be created.
     *
     * @param key
     * @param values
     * @throws java.lang.NullPointerException if {@code key} or any {@code value} is null
     */
    void addAll(K key, List<V> values);

}
