package org.joyrest.routing.entity;

import java.util.List;

public class RequestCollectionType<T> extends CollectionType<T> {

	public RequestCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> RequestCollectionType<List<P>> ReqList(Class<P> param) {
		return new RequestCollectionType<>(List.class, param);
	}

}
