package org.joyrest.context.helper;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static org.joyrest.utils.CollectionUtils.insertInto;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.joyrest.common.annotation.Ordered;
import org.joyrest.common.annotation.General;
import org.joyrest.transform.Transformer;

public final class ConfigurationHelper {

	public static <T extends Ordered> Collection<T> sort(Collection<T> collection) {
		Comparator<T> aspectComparator =
				(e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder());

		return collection.stream()
			.sorted(aspectComparator)
			.collect(toList());
	}

	public static <T extends Transformer> Map<Boolean, List<T>> createTransformers(Collection<T> transform) {
		return transform.stream()
			.collect(partitioningBy(General::isGeneral));
	}
}
