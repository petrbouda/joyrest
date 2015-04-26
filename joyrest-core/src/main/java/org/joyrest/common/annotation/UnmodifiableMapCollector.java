package org.joyrest.common.annotation;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UnmodifiableMapCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {

	private final BiConsumer<Map<K, V>, T> accumulator;

	public UnmodifiableMapCollector(BiConsumer<Map<K, V>, T> accumulator) {
		this.accumulator = accumulator;
	}

	@Override
	public Supplier<Map<K, V>> supplier() {
		return HashMap::new;
	}

	@Override
	public BiConsumer<Map<K, V>, T> accumulator() {
		return accumulator;
	}

	@Override
	public BinaryOperator<Map<K, V>> combiner() {
		return (left, right) -> {
			left.putAll(right);
			return left;
		};
	}

	@Override
	public Function<Map<K, V>, Map<K, V>> finisher() {
		return Collections::unmodifiableMap;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return EnumSet.of(Characteristics.UNORDERED);
	}

	public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toUnmodifiableMap(
			Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {

		BiConsumer<Map<K, V>, T> accumulator = (map, element) ->
				map.merge(keyMapper.apply(element), valueMapper.apply(element),
						throwingMerger());

		return new UnmodifiableMapCollector<>(accumulator);
	}

	private static <T> BinaryOperator<T> throwingMerger() {
		return (u, v) -> {
			throw new IllegalStateException(String.format("Duplicate key %s", u));
		};
	}
}
