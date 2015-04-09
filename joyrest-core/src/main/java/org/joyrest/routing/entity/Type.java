package org.joyrest.routing.entity;

public class Type<T> {

	private final Class<?> type;

	public Type(Class<?> type) {
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}
}
