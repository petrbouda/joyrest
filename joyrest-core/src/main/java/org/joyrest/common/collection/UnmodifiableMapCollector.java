/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.common.collection;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Collector for unmodifiable map.
 *
 * @param <T> the type of input elements to the reduction operation
 * @param <K> the mutable accumulation type of the reduction operation (often hidden as an implementation detail)
 * @param <V> the result type of the reduction operation
 *
 * @author pbouda
 */
public class UnmodifiableMapCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {

	private final BiConsumer<Map<K, V>, T> accumulator;

	public UnmodifiableMapCollector(BiConsumer<Map<K, V>, T> accumulator) {
		this.accumulator = accumulator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Supplier<Map<K, V>> supplier() {
		return HashMap::new;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BiConsumer<Map<K, V>, T> accumulator() {
		return accumulator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BinaryOperator<Map<K, V>> combiner() {
		return (left, right) -> {
			left.putAll(right);
			return left;
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function<Map<K, V>, Map<K, V>> finisher() {
		return Collections::unmodifiableMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Characteristics> characteristics() {
		return EnumSet.of(Characteristics.UNORDERED);
	}

	/**
	 * Method creates {@link UnmodifiableMapCollector} according to the {@code keyMapper} and {@code valueMapper}
	 *
	 * @param keyMapper {@link Function} that defines how to map a key in the map
	 * @param valueMapper {@link Function} that defines how to map a value in the map
	 * @param <T> the type of input elements to the reduction operation
	 * @param <K> the mutable accumulation type of the reduction operation (often hidden as an implementation detail)
	 * @param <V> the result type of the reduction operation
	 * @return unmodifiable-map collector
	 */
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
