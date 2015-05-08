package org.joyrest.routing;

import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

import java.util.function.BiConsumer;

public interface RouteAction<REQ, RESP> extends BiConsumer<Request<REQ>, Response<RESP>> {

}
