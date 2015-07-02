package org.joyrest.common;

import static java.util.function.Function.identity;
import static org.joyrest.common.collection.UnmodifiableMapCollector.toUnmodifiableMap;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Test;

public class UnmodifiableMapCollectorTest {

	@Test
	public void testToUnmodifiableMap() {
		Map<Integer, Integer> map = IntStream.rangeClosed(1, 4)
			.mapToObj(Integer::valueOf)
			.collect(toUnmodifiableMap(identity(), identity()));

		assertEquals(4, map.size());
		assertEquals(map.get(1), Integer.valueOf(1));
		assertEquals(map.get(2), Integer.valueOf(2));
		assertEquals(map.get(3), Integer.valueOf(3));
		assertEquals(map.get(4), Integer.valueOf(4));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testToUnmodifiableMapException() {
		Map<Integer, Integer> map = IntStream.rangeClosed(1, 4)
			.mapToObj(Integer::valueOf)
			.collect(toUnmodifiableMap(identity(), identity()));

		map.put(5, 5);
	}

}