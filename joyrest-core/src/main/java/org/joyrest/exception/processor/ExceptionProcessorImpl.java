package org.joyrest.exception.processor;

import java.util.Map;
import java.util.Optional;

import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

public class ExceptionProcessorImpl implements ExceptionProcessor {

	private final Map<Class<? extends Exception>, TriConsumer<Request, Response, ? extends Exception>> handlers;

	public ExceptionProcessorImpl(ApplicationContext config) {
		this.handlers = config.getExceptionHandlers();
	}

	@Override
	public Response process(final Exception ex, final InternalRequest request, final InternalResponse response)
			throws Exception {
		Class<? extends Exception> clazz = ex.getClass();
		ExceptionHandler<? super Exception> handler = getHandler(clazz);

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
			ExceptionHandler<? super Exception> handler = getHandler(superClazz);
			if (handler != null) {
				return Optional.of(handler);
			}

			superClazz = superClazz.getSuperclass();
		}

		return Optional.empty();
	}

	@SuppressWarnings("all")
	private ExceptionHandler<? super Exception> getHandler(Class<?> clazz) {
		return (ExceptionHandler<? super Exception>) handlers.get(clazz);
	}
}