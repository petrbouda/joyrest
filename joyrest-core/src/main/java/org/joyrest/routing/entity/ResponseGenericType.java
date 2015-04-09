package org.joyrest.routing.entity;

import java.util.List;

public class ResponseGenericType<T> extends GenericType<T> {

	public ResponseGenericType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> ResponseGenericType<List<P>> RespList(Class<P> param) {
		return new ResponseGenericType<>(List.class, param);
	}

}
