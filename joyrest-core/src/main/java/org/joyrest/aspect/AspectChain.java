package org.joyrest.aspect;

import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;

public interface AspectChain {

	Response proceed(Request request, Response response);

	Route getRoute();

}
