package org.joyrest.exception.processor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;

import java.util.Map;
import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.transform.Writer;

public class ExceptionProcessorImpl implements ExceptionProcessor {

	private final Map<Class<? extends Exception>, TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>> handlers;

	private final Map<MediaType, Writer> writers;

	public ExceptionProcessorImpl(ApplicationContext config) {
		this.handlers = config.getExceptionHandlers();
		this.writers = config.getExceptionWriters();
	}

	@Override
	public <T extends Exception> InternalResponse<?> process(T ex, InternalRequest<?> request, InternalResponse<?> response)
			throws Exception {
		Class<? extends Exception> clazz = ex.getClass();
		@SuppressWarnings("rawtypes")
		TriConsumer handler = handlers.get(ex.getClass());

		if (isNull(handler))
			handler = getHandlerFromParent(clazz).orElseThrow(() -> ex);

		handler.accept(request, response, ex);
		writeEntity(request, response);
		return response;
	}

	private static Writer chooseWriter(Map<MediaType, Writer> writers, MediaType acceptHeader) {
		Writer writer = writers.get(acceptHeader);
		if (isNull(writer))
			writer = writers.get(acceptHeader.getProcessingType()
				.orElseThrow(internalServerErrorSupplier()));

		if (isNull(writer))
			throw internalServerErrorSupplier().get();

		return writer;
	}

	private void writeEntity(final InternalRequest<?> request, final InternalResponse<?> response) {
		if (response.getEntity().isPresent()) {
			MediaType acceptHeader = request.getHeader(ACCEPT).map(MediaType::of).get();
			Writer writer = chooseWriter(writers, acceptHeader);
			writer.writeTo(response);
		}
	}

	private Optional<TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>>
			getHandlerFromParent(Class<? extends Exception> clazz) {
		if (clazz == Exception.class) {
			return Optional.empty();
		}

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != Exception.class) {
			TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception> handler = handlers.get(clazz);
			if (nonNull(handler))
				return Optional.of(handler);

			superClazz = superClazz.getSuperclass();
		}

		return Optional.empty();
	}
}