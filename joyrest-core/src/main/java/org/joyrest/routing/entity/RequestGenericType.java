package org.joyrest.routing.entity;

import java.util.List;

public class RequestGenericType<T> extends GenericType<T> {

	public RequestGenericType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> RequestGenericType<List<P>> ReqList(Class<P> param) {
		return new RequestGenericType<>(List.class, param);
	}

}
