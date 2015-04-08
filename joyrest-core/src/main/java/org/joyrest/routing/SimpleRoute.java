package org.joyrest.routing;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

import java.util.Optional;
import java.util.function.BiConsumer;

public class SimpleRoute<REQ, RESP> extends AbstractRoute<REQ, RESP> {

	/* route action which contains information about a request body */
	private final BiConsumer<Request<REQ>, Response<RESP>> action;

	/**
	 * @param path       entire path of the route
	 * @param httpMethod http method which belongs to this route
	 * @param action     action what will be executed if the route is called
	 */
	public SimpleRoute(String path, HttpMethod httpMethod, BiConsumer<Request<REQ>, Response<RESP>> action) {
		super(path, httpMethod);
		this.action = action;
	}

	@Override
	public SimpleRoute produces(MediaType... produces) {
		super.produces(produces);
		return this;
	}


	@Override
	public Optional<Class<REQ>> getRequestBodyClass() {
		return Optional.empty();
	}

	@Override
	public Optional<Class<RESP>> getResponseBodyClass() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param request
	 */
	@Override
	public InternalResponse<RESP> execute(final InternalRequest<REQ> request, InternalResponse<RESP> response) {
		action.accept(request, response);
		return response;
	}

}
