package org.joyrest.exception.processor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import java.util.List;
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
	public <T extends Exception> InternalResponse<Object> process(T ex, InternalRequest<Object> request, InternalResponse<Object> response)
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
			List<MediaType> acceptMediaTypes = request.getAccept().get();
			Writer writer = acceptMediaTypes.stream()
				.filter(accept -> handler.getWriter(accept).isPresent())
				.findFirst()
				.flatMap(handler::getWriter)
				.orElseThrow(internalServerErrorSupplier(
						String.format("No writer registered for Accept%s and Exception Response-Type[%s]",
							acceptMediaTypes, handler.getExceptionClass())));

			response.header(CONTENT_TYPE, writer.getMediaType().get());
			writer.writeTo(response, request);
		}
	}

	private Optional<InternalExceptionHandler> getHandlerFromParent(Class<? extends Exception> clazz) {
		if (clazz == Exception.class) {
			return Optional.empty();
		}

		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != Exception.class) {
			InternalExceptionHandler handler = handlers.get(superClazz);
			if (nonNull(handler))
				return Optional.of(handler);

			superClazz = superClazz.getSuperclass();
		}

		return Optional.empty();
	}
}