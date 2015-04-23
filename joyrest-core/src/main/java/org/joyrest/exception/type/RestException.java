package org.joyrest.exception.type;

import java.util.function.Supplier;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 3036905776427215166L;

	private final InternalResponse<?> response;

	public RestException(HttpStatus status) {
		this(new InternalResponse().status(status), "");
	}

	public RestException(HttpStatus status, String message) {
		this(new InternalResponse().status(status), message);
	}

	public RestException(InternalResponse<?> response) {
		this(response, "");
	}

	public RestException(InternalResponse<?> response, String message) {
		super(message);
		this.response = response;
	}

	public static Supplier<RestException> badRequestSupplier() {
		return () -> new RestException(HttpStatus.BAD_REQUEST, "Bad Request");
	}

	public static Supplier<RestException> notFoundSupplier() {
		return () -> new RestException(HttpStatus.NOT_FOUND, "Not Found");
	}

	public static Supplier<RestException> unsupportedMediaTypeSupplier() {
		return () -> new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
	}

	public static Supplier<RestException> notAcceptableSupplier() {
		return () -> new RestException(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable");
	}

	public static Supplier<RestException> internalServerErrorSupplier() {
		return () -> new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
	}

	public static Supplier<RestException> internalServerErrorSupplier(String msg) {
		return () -> new RestException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}

	public InternalResponse<?> getResponse() {
		return response;
	}

}
