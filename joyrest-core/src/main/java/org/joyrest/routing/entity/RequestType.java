package org.joyrest.routing.entity;

public class RequestType<T> extends Type<T> {

	public RequestType(Class<?> type) {
		super(type);
	}

	public static <P> RequestType<P> Req(Class<P> param) {
		return new RequestType<>(param);
	}

}
