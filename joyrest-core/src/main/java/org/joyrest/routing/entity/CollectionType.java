package org.joyrest.routing.entity;

public class CollectionType<T> extends Type<T> {

	private final Class<?> param;

	public CollectionType(Class<?> type, Class<?> param) {
		super(type);
		this.param = param;
	}

	public Class<?> getParam() {
		return param;
	}

	public String toString() {
		return getType().getSimpleName() + "<" + param.getSimpleName() + ">";
	}
}
