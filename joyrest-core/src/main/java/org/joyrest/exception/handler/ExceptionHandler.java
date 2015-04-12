package org.joyrest.exception.handler;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

@FunctionalInterface
public interface ExceptionHandler<T extends Exception> extends TriConsumer<InternalRequest<?>, InternalResponse<?>, T> {

}
