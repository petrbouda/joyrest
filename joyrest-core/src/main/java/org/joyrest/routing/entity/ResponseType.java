package org.joyrest.routing.entity;

public class ResponseType<T> extends Type<T> {

	public ResponseType(Class<?> type) {
		super(type);
	}

	public static <P> ResponseType<P> Resp(Class<P> param) {
		return new ResponseType<>(param);
	}
}
