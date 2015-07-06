package org.joyrest.utils;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

	public static <T> List<T> concat(List<? extends T> a, List<? extends T> b, List<? extends T> c) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		Objects.requireNonNull(c);

		Stream<T> first = Stream.concat(a.stream(), b.stream());
		return Stream.concat(first, c.stream()).collect(toList());
	}

	public static <T> List<T> concat(List<? extends T> a, List<? extends T> b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);

		return Stream.concat(a.stream(), b.stream()).collect(toList());
	}
}
