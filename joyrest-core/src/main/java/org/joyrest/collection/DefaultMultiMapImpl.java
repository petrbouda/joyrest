package org.joyrest.collection;

import org.joyrest.collection.annotation.Default;
import org.joyrest.exception.type.InvalidDefaultEntryException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
public class DefaultMultiMapImpl<K, V extends Default> extends MultiMapImpl<K, V> implements DefaultMultiMap<K, V> {

    public static final String ALREADY_CONTAINS_DEFAULT = "A default entity has been already added into the map.";
    public static final String TOO_MANY_DEFAULTS = "The key '%s' contains values with more than one Default Entity.";

    DefaultMultiMapImpl(Map<K, List<V>> datastore) {
        super(datastore);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(K key, V value) {
        if(value.isDefault() && datastoreContainsDefault(key)) {
            throw new InvalidDefaultEntryException(ALREADY_CONTAINS_DEFAULT);
        }

        List<V> list = super.get(key);
        if(list != null) {
            list.add(value);
        } else {
            put(key, Collections.singletonList(value));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(K key, List<V> values) {
        if(valuesContainDefault(values) && datastoreContainsDefault(key)) {
            throw new InvalidDefaultEntryException(ALREADY_CONTAINS_DEFAULT);
        }

        List<V> list = super.get(key);
        if(list != null) {
            list.addAll(values);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> put(K key, List<V> values) {
        valuesDefaultCheck(key, values);
        return super.put(key, values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> map) {
        map.entrySet().stream().forEach(entry ->
                valuesDefaultCheck(entry.getKey(), entry.getValue()));
        super.putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> getAll(K key){
        return super.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<V> getDefault(K key) {
        List<V> list = super.get(key);
        if(list == null) {
            return Optional.empty();
        }

        return list.stream().filter(Default::isDefault).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> getAllNonDefault(K key) {
        List<V> list = super.get(key);
        if(list == null) {
            return new ArrayList<>();
        }

        return list.stream()
            .filter(val -> !val.isDefault()).collect(Collectors.toList());
    }


    private void valuesDefaultCheck(K key, List<V> values) {
        long count = values.stream().filter(Default::isDefault).count();
        if(count > 1) {
            throw new InvalidDefaultEntryException(String.format(TOO_MANY_DEFAULTS, key));
        }
    }

    private boolean datastoreContainsDefault(K key) {
        return getDefault(key).isPresent();
    }

    private boolean valuesContainDefault(List<V> values) {
        return values.stream().anyMatch(Default::isDefault);
    }
}
