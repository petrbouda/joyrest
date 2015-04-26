package org.joyrest.extractor.param;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public abstract class AbstractPath<T> implements PathType<T> {

	private final String name;

	private final Function<String, T> extractor;

	public AbstractPath(String name, Function<String, T> extractor) {
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
		final AbstractPath other = (AbstractPath) obj;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.extractor, other.extractor);
	}
}
