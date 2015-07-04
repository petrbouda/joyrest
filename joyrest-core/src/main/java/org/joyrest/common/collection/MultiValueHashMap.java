/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import org.joyrest.utils.CollectionUtils;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MultiValueHashMap<K, V> extends HashMap<K, List<V>> implements MultiValueMap<K, V> {

	public MultiValueHashMap() {
		super(new HashMap<>());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addAll(K key, V... value) {
		addAll(key, asList(value));
	}

	@Override
	public void addAll(K key, List<V> values) {
		requireNonNull(values);

		if(CollectionUtils.isEmpty(values))
			return;

		List<V> curValues = get(key);
		values.stream()
			.filter(Objects::nonNull)
			.forEach(curValues::add);
	}

	@Override
	public void add(K key, V value) {
		requireNonNull(value);

		get(key).add(value);
	}
}
