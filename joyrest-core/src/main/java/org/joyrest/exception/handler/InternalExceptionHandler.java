package org.joyrest.exception.handler;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.Writer;

public class InternalExceptionHandler implements ExceptionHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final TriConsumer action;
	private final Type<?> responseType;
	private final Class<? extends Exception> exceptionClass;
	private Map<MediaType, Writer> writers = new HashMap<>();

	public <T extends Exception, RESP> InternalExceptionHandler(Class<T> clazz,
		TriConsumer<Request<?>, Response<RESP>, T> action, Type<RESP> resp) {
		this.exceptionClass = clazz;
		this.action = action;
		this.responseType = resp;
	}

	public Class<? extends Exception> getExceptionClass() {
		return exceptionClass;
	}

	public Type<?> getResponseType() {
		return responseType;
	}

	public void addWriter(Writer writer) {
		requireNonNull(writer);
		this.writers.put(writer.getMediaType(), writer);
	}

	public Optional<Writer> getWriter(MediaType mediaType) {
		return Optional.ofNullable(writers.get(mediaType));
	}

	public Map<MediaType, Writer> getWriters() {
		return writers;
	}

	public <T extends Exception> InternalResponse<?> execute(InternalRequest<?> request, InternalResponse<?> response, T ex) {
		action.accept(ImmutableRequest.of(request), response, ex);
		return response;
	}

}
