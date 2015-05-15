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
package org.joyrest.extractor.param;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public abstract class AbstractVariable<T> implements VariableType<T> {

	private final String name;

	private final Function<String, T> extractor;

	public AbstractVariable(String name, Function<String, T> extractor) {
		this.name = name;
		this.extractor = extractor;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<T> valueOf(String value) {
		return Optional.ofNullable(extractor.apply(value));
	}

	@Override
	public boolean isAssignableFromString(String value) {
		return nonNull(extractor.apply(value));
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, extractor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final AbstractVariable other = (AbstractVariable) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.extractor, other.extractor);
	}
}
