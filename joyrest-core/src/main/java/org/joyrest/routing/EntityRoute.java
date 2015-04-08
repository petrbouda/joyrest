package org.joyrest.routing;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EntityRoute<REQ, RESP> extends AbstractRoute<REQ, RESP> {

	private TriConsumer<Request<REQ>, Response<RESP>, REQ> triAction;
	private BiConsumer<Request<REQ>, Response<RESP>> biAction;

	private Class<REQ> requestBodyClass;
	private Class<RESP> responseBodyClass;

	public EntityRoute(String path, HttpMethod httpMethod, TriConsumer<Request<REQ>, Response<RESP>, REQ> triAction,
		Class<REQ> requestClazz) {
		this(path, httpMethod, triAction, requestClazz, null);
	}

	public EntityRoute(String path, HttpMethod httpMethod, TriConsumer<Request<REQ>, Response<RESP>, REQ> triAction,
					   Class<REQ> requestClazz, Class<RESP> responseClazz) {
		super(path, httpMethod);
		this.triAction = triAction;
		this.requestBodyClass = requestClazz;
		this.responseBodyClass = responseClazz;
	}

	public EntityRoute(String path, HttpMethod httpMethod, BiConsumer<Request<REQ>, Response<RESP>> biAction,
					   Class<REQ> requestClazz, Class<RESP> responseClazz) {
		super(path, httpMethod);
		this.biAction = biAction;
		this.requestBodyClass = requestClazz;
		this.responseBodyClass = responseClazz;
	}

	@Override
	public EntityRoute<REQ, RESP> consumes(MediaType... consumes) {
		super.consumes(consumes);
		return this;
	}

	@Override
	public EntityRoute<REQ, RESP> produces(MediaType... produces) {
		super.produces(produces);
		return this;
	}

	@Override
	public Optional<Class<REQ>> getRequestBodyClass() {
		return Optional.ofNullable(requestBodyClass);
	}

	@Override
	public Optional<Class<RESP>> getResponseBodyClass() {
		return Optional.ofNullable(responseBodyClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param request
	 */
	@Override
	public InternalResponse<RESP> execute(InternalRequest<REQ> request, InternalResponse<RESP> response) {
		@SuppressWarnings("unchecked")
		final Request<REQ> immutableRequest = ImmutableRequest.of(request);
		if (triAction == null) {
			triAction.accept(immutableRequest, response, requestBodyClass.cast(request.getEntity().get()));
		} else {
			biAction.accept(immutableRequest, response);
		}
		return response;
	}
}
