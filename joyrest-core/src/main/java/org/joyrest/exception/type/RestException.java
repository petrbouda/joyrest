package org.joyrest.exception.type;

import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

import java.util.function.Supplier;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 3036905776427215166L;

	private final Response response;

	public RestException(HttpStatus status) {
		this(new InternalResponse().status(status), "");
	}

	public RestException(HttpStatus status, String message) {
		this(new InternalResponse().status(status), message);
	}

	public RestException(Response response) {
		this(response, "");
	}

	public RestException(Response response, String message) {
		super(message);
		this.response = response;
	}

	public Response getResponse() {
		return response;
	}

	public static Supplier<RestException> badRequestSupplier() {
		return () -> new RestException(HttpStatus.BAD_REQUEST);
	}

	public static Supplier<RestException> notFoundSupplier() {
		return () -> new RestException(HttpStatus.NOT_FOUND);
	}

	public static Supplier<RestException> unsupportedMediaTypeSupplier() {
		return () -> new RestException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	public static Supplier<RestException> notAcceptableSupplier() {
		return () -> new RestException(HttpStatus.NOT_ACCEPTABLE);
	}

	public static Supplier<RestException> internalServerErrorSupplier() {
		return () -> new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
