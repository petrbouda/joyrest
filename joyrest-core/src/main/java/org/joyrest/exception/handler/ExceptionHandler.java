package org.joyrest.exception.handler;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

@FunctionalInterface
public interface ExceptionHandler<T extends Exception> extends TriConsumer<InternalRequest<?>, InternalResponse<?>, T> {

}
