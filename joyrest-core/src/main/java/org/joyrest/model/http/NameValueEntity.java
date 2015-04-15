package org.joyrest.model.http;

import java.util.Objects;

public class NameValueEntity<N, V> {

	private final N name;

	private final V value;

	public NameValueEntity(N name, V value) {
		this.name = name;
		this.value = value;
	}

	public N getName() {
		return name;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final NameValueEntity<?, ?> other = (NameValueEntity<?, ?>) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.value, other.value);
	}
}
