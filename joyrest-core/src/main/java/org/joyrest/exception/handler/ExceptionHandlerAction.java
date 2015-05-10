package org.joyrest.exception.handler;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

public interface ExceptionHandlerAction<RESP, T extends Exception> extends TriConsumer<Request<?>, Response<RESP>, T> {

}
