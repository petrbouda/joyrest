package org.joyrest.utils;

import static java.util.Objects.isNull;

import java.util.Collection;

public class CollectionUtils {

	public static final boolean nonEmpty(Collection<?> col) {
		return col != null && !col.isEmpty();
	}

	public static final boolean isSingletonList(Collection<?> col) {
		return col != null && col.size() == 1;
	}

	public static final <T> Collection<T> insertInto(Collection<T> collection, T element) {
		if (isNull(element))
			return collection;

		collection.add(element);
		return collection;
	}

	public static final <T> Collection<T> insertInto(Collection<T> collection, Collection<T> insert) {
		if (isNull(insert))
			return collection;

		collection.addAll(insert);
		return collection;
	}
}
