package org.joyrest.exception.processor;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;

import java.util.Map;
import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.Writer;

public class ExceptionProcessorImpl implements ExceptionProcessor {

	private final Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> handlers;

	private final Map<MediaType, Writer> writers;

	public ExceptionProcessorImpl(ApplicationContext config) {
		this.handlers = config.getExceptionHandlers();
		this.writers = config.getExceptionWriters();
	}

	private static Writer chooseWriter(Map<MediaType, Writer> writers, MediaType acceptHeader) {
		Writer writer = writers.get(acceptHeader);
		if (writer == null)
			writers.get(acceptHeader.getProcessingType()
				.orElseThrow(internalServerErrorSupplier()));

		if (writer == null)
			throw internalServerErrorSupplier().get();

		return writer;
	}

	@Override
	public <T extends Exception> InternalResponse<?> process(T ex, InternalRequest<?> request, InternalResponse<?> response)
			throws Exception {
		if (ex instanceof RestException) {
			InternalResponse<?> exResponse = ((RestException) ex).getResponse();
			response.status(exResponse.getStatus());
			exResponse.getHeaders().forEach(response::header);
			exResponse.setOutputStream(response.getOutputStream());
			writeEntity(request, response);
			return response;
		} else {
			return callHandler(ex, request, response);
		}
	}

	private <T extends Exception> InternalResponse<?> callHandler(T ex, InternalRequest<?> request, InternalResponse<?> response)
			throws Exception {
		Class<? extends Exception> clazz = ex.getClass();
		ExceptionHandler<? super Exception> handler = handlers.get(clazz);

		if (handler == null) {
			Optional<ExceptionHandler<? super Exception>> optHandler = getHandlerFromParent(clazz);
			handler = optHandler.orElseThrow(() -> ex);
		}

		handler.accept(request, response, ex);
		return response;
	}

	private Optional<ExceptionHandler<? super Exception>> getHandlerFromParent(Class<? extends Exception> clazz) {
		if (clazz == Exception.class) {
			return Optional.empty();
		}

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != Exception.class) {
			ExceptionHandler<? super Exception> handler = handlers.get(clazz);
			if (handler != null) {
				return Optional.of(handler);
			}

			superClazz = superClazz.getSuperclass();
		}

		return Optional.empty();
	}

	private void writeEntity(final InternalRequest<?> request, final InternalResponse<?> response) {
		if (response.getEntity().isPresent()) {
			MediaType acceptHeader = request.getHeader(ACCEPT).map(MediaType::of).get();
			Writer writer = chooseWriter(writers, acceptHeader);
			writer.writeTo(response);
		}
	}
}