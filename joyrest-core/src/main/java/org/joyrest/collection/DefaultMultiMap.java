package org.joyrest.collection;

import org.joyrest.collection.annotation.Default;
import org.joyrest.exception.type.InvalidDefaultEntryException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Typical multi value map implementing and accepts only {@code values} that implement
 * {@link org.joyrest.collection.annotation.Default} interface for purposes to provide specific operation upon this map.
 * <p>
 * It is allowed only one {@code entity} in the map that returns {@code true} from the {@link Default#isDefault()}
 * stored along with given key.
 * </p>
 *
 * @see org.joyrest.collection.annotation.Default
 * @see org.joyrest.collection.JoyCollections
 * @see org.joyrest.collection.DefaultMultiMapImpl
 * @see org.joyrest.collection.ImmutableDefaultMultiMap
 * @param <K> key
 * @param <V> values extends {@link org.joyrest.collection.annotation.Default} interface
 * @author boudap
 */
public interface DefaultMultiMap<K, V extends Default> extends MultiMap<K, V> {

    /**
     * Adds {@code value} to the existing {@code key}. If the {@code key} does not exist it will be created.
     *
     * @param key
     * @param value
     * @throws InvalidDefaultEntryException map has already contains a default entity along with given key
     */
    @Override
    void add(K key, V value) throws InvalidDefaultEntryException;

    /**
     * Adds {@code values} to the existing {@code key}. If the {@code key} does not exist it will be created.
     *
     * @param key
     * @param values
     * @throws InvalidDefaultEntryException map has already contains a default entity along with given key
     */
    @Override
    void addAll(K key, List<V> values) throws InvalidDefaultEntryException;

    /**
     * {@inheritDoc}
     * @throws InvalidDefaultEntryException map has already contains a default entity along with given key
     */
    @Override
    List<V> put(K key, List<V> value) throws InvalidDefaultEntryException;

    /**
     * {@inheritDoc}
     * @throws InvalidDefaultEntryException map has already contains a default entity along with given key
     */
    @Override
    void putAll(Map<? extends K, ? extends List<V>> m) throws InvalidDefaultEntryException;

    /**
     * Returns list of values which are stored under the same {@code key}
     *
     * @param key
     * @return collection of values
     */
    List<V> getAll(K key);

    /**
     * Returns {@code entity} that extends {@link org.joyrest.collection.annotation.Default} interface if the given entity exists
     * in the map and returns {@code true} from method {@link org.joyrest.collection.annotation.Default#isDefault()}.
     *
     * @param key key whose default entity will be returned
     * @return {@code entity} if the default entity exists in the map,
     *          returns {@link Optional#EMPTY } if there is no default entity
     */
    Optional<V> getDefault(K key);

    /**
     * Returns list of values which are stored under the same {@code key} and returns {@code false} from method
     * {@link org.joyrest.collection.annotation.Default#isDefault()}
     *
     * @param key key whose values will be returned apart from default entity
     * @return collection of values
     */
    List<V> getAllNonDefault(K key);

}
