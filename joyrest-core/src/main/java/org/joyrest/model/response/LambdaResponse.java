package org.joyrest.model.response;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public class LambdaResponse<E> extends InternalResponse<E> {

	private final BiConsumer<String, String> headerConsumer;

	private final Consumer<HttpStatus> statusConsumer;

	public LambdaResponse(BiConsumer<String, String> headerConsumer, Consumer<HttpStatus> statusConsumer) {
		requireNonNull(headerConsumer, "HeaderConsumer cannot be null in Response object.");
		requireNonNull(statusConsumer, "HeaderConsumer cannot be null in Response object.");

		this.headerConsumer = headerConsumer;
		this.statusConsumer = statusConsumer;
	}

	@Override
	public LambdaResponse header(HeaderName name, String value) {
		super.header(name, value);
		headerConsumer.accept(name.getValue(), value);
		return this;
	}

	@Override
	public LambdaResponse status(HttpStatus status) {
		super.status(status);
		statusConsumer.accept(status);
		return this;
	}

}