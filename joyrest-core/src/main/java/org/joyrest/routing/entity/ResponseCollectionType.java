package org.joyrest.routing.entity;

import java.util.List;

public class ResponseCollectionType<T> extends CollectionType<T> {

	public ResponseCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> ResponseCollectionType<List<P>> RespList(Class<P> param) {
		return new ResponseCollectionType<>(List.class, param);
	}

}
