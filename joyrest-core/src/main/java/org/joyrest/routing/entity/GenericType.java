package org.joyrest.routing.entity;

public class GenericType<T> extends Type<T>{

	private final Class<?> param;

	public GenericType(Class<?> type, Class<?> param) {
		super(type);
		this.param = param;
	}

	public Class<?> getParam() {
		return param;
	}
}
