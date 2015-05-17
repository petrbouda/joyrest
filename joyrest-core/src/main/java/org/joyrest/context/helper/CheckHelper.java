package org.joyrest.context.helper;

import org.joyrest.common.annotation.Ordered;
import org.joyrest.exception.type.InvalidConfigurationException;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public final class CheckHelper {

	public static <T extends Ordered> void orderDuplicationCheck(Collection<T> collection) {
		Set<T> duplicated = collection.stream()
			.filter(n -> collection.stream()
				.filter(x -> x.getOrder() == n.getOrder()).count() > 1)
			.collect(toSet());

		if (!duplicated.isEmpty()) {
			String duplicatedOrders = duplicated.stream()
				.map(i -> String.valueOf(i.getOrder()))
				.collect(joining(", "));

			throw new InvalidConfigurationException(
				"There is registered more than one entity with the given order: " + duplicatedOrders);
		}
	}
}
