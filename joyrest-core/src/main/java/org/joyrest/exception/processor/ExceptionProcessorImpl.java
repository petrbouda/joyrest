package org.joyrest.exception.processor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;

import java.util.Map;
import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.Writer;

public class ExceptionProcessorImpl implements ExceptionProcessor {

	private final Map<Class<? extends Exception>, InternalExceptionHandler> handlers;

	public ExceptionProcessorImpl(ApplicationContext config) {
		this.handlers = config.getExceptionHandlers();
	}

	@Override
	public <T extends Exception> InternalResponse<?> process(T ex, InternalRequest<?> request, InternalResponse<?> response)
			throws Exception {
		Class<? extends Exception> clazz = ex.getClass();
		InternalExceptionHandler handler = handlers.get(clazz);

		if (isNull(handler))
			handler = getHandlerFromParent(clazz).orElseThrow(() -> ex);

		handler.execute(request, response, ex);
		writeEntity(handler, request, response);
		return response;
	}

	private void writeEntity(InternalExceptionHandler handler, InternalRequest<?> request, InternalResponse<?> response) {
		if (response.getEntity().isPresent()) {
			MediaType acceptHeader = request.getHeader(ACCEPT).map(MediaType::of).get();
			Writer writer = handler.getWriter(acceptHeader)
				.orElseThrow(internalServerErrorSupplier());
			writer.writeTo(response);
		}
	}

	private Optional<InternalExceptionHandler> getHandlerFromParent(Class<? extends Exception> clazz) {
		if (clazz == Exception.class) {
			return Optional.empty();
		}

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != Exception.class) {
			InternalExceptionHandler handler = handlers.get(clazz);
			if (nonNull(handler))
				return Optional.of(handler);

			superClazz = superClazz.getSuperclass();
		}

		return Optional.empty();
	}
}