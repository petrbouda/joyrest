package org.joyrest.routing.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ResponseCollectionType<T> extends CollectionType<T> {

	public ResponseCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> ResponseCollectionType<List<P>> RespList(Class<P> param) {
		return new ResponseCollectionType<>(List.class, param);
	}

	public static <P> ResponseCollectionType<Set<P>> RespSet(Class<P> param) {
		return new ResponseCollectionType<>(Set.class, param);
	}

	public static <P> ResponseCollectionType<Collection<P>> RespCol(Class<P> param) {
		return new ResponseCollectionType<>(Collection.class, param);
	}

}
