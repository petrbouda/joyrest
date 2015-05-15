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
package org.joyrest.model;

import org.joyrest.extractor.param.VariableType;

import java.util.Objects;

public class RoutePart<T> {

	private final Type type;
	private final VariableType<T> variableType;
	private final String value;

	public RoutePart(Type type, String value, VariableType<T> variableType) {
		this.type = type;
		this.value = value;
		this.variableType = variableType;
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public VariableType<T> getVariableType() {
		return variableType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, variableType, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final RoutePart other = (RoutePart) obj;
		return Objects.equals(this.type, other.type)
			&& Objects.equals(this.variableType, other.variableType)
			&& Objects.equals(this.value, other.value);
	}

	public enum Type {
		PATH,
		PARAM
	}
}
