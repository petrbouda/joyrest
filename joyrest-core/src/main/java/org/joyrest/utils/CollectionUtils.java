package org.joyrest.utils;

import java.util.Collection;

public class CollectionUtils {

	public static final boolean nonEmpty(Collection<?> col) {
		return col != null && !col.isEmpty();
	}

	public static final boolean isSingletonList(Collection<?> col) {
		return col != null && col.size() == 1;
	}

}
