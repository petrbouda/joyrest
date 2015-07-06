package org.joyrest.utils;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtils {

	public static boolean nonEmpty(Collection<?> col) {
		return col != null && !col.isEmpty();
	}

	public static boolean isEmpty(Collection<?> col) {
		return col != null && col.isEmpty();
	}

	public static boolean isSingletonList(Collection<?> col) {
		return col != null && col.size() == 1;
	}

	public static <T> Collection<T> insertInto(Collection<T> collection, T element) {
		if (isNull(element))
			return collection;

		collection.add(element);
		return collection;
	}

	public static <T1, T2 extends T1> Collection<T1> insertInto(Collection<T1> collection, Collection<T2> insert) {
		if (isNull(insert))
			return collection;

		collection.addAll(insert);
		return collection;
	}

	public static <T> Collection<T> insertIntoNewList(Collection<T> collection, T insert) {
		if (isNull(insert))
			return new ArrayList<>(collection);

		List<T> list = new ArrayList<>(collection);
		Collections.addAll(list, insert);
		return list;
	}

	public static <T, T2 extends T> Collection<T> insertIntoNewList(Collection<T> collection, Collection<T2> insert) {
		if (isNull(insert))
			return new ArrayList<>(collection);

		List<T> list = new ArrayList<>(collection);
		list.addAll(insert);
		return list;
	}
}
