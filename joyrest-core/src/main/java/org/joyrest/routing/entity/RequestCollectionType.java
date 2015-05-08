package org.joyrest.routing.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RequestCollectionType<T> extends CollectionType<T> {

	public RequestCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> RequestCollectionType<List<P>> ReqList(Class<P> param) {
		return new RequestCollectionType<>(List.class, param);
	}

	public static <P> RequestCollectionType<Set<P>> ReqSet(Class<P> param) {
		return new RequestCollectionType<>(Set.class, param);
	}

	public static <P> RequestCollectionType<Collection<P>> ReqCol(Class<P> param) {
		return new RequestCollectionType<>(Collection.class, param);
	}
}
