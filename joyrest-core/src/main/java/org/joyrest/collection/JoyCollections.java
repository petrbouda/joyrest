package org.joyrest.collection;

import org.joyrest.collection.annotation.Default;

import java.util.HashMap;

public final class JoyCollections {

    private JoyCollections() {}

    public static <K, V> MultiMap<K, V> hashMultiMap() {
        return new MultiMapImpl<>(new HashMap<>());
    }

    public static <K, V extends Default> DefaultMultiMap<K, V> defaultHashMultiMap() {
        return new DefaultMultiMapImpl<>(new HashMap<>());
    }

    public static final <K, V> ImmutableMultiMap<K, V> immutableOf(MultiMap<K, V> datastore) {
        return new ImmutableMultiMap<>(datastore);
    }

    public static final <K, V extends Default> ImmutableDefaultMultiMap<K, V> immutableOf(DefaultMultiMap<K, V> datastore) {
        return new ImmutableDefaultMultiMap<>(datastore);
    }

}
