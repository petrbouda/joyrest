package org.joyrest.routing.entity;

import java.util.List;

public class GenericType<P> {

	private final Class<?> type;

	private final Class<?> param;

	public GenericType(Class<?> type, Class<?> param) {
		this.type = type;
		this.param = param;
	}

	public static <P> GenericType<List<P>> List(Class<P> param) {
		return new GenericType<>(List.class, param);
	}

	public Class<?> getType() {
		return type;
	}

	public Class<?> getParam() {
		return param;
	}
}
