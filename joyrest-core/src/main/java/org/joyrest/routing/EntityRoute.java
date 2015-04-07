package org.joyrest.routing;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.ImmutableRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

import java.util.Optional;

/**
 * Container for all information about one route {@link EntityRoute} that contains body information.
 *
 * @author pbouda
 */
public class EntityRoute<T> extends AbstractRoute {

	private final TriConsumer<Request, Response, T> action;

	private final Class<T> requestBodyClass;

	/**
	 * @param path       entire path of the route
	 * @param httpMethod http method which belongs to this route
	 * @param action     action what will be executed if the route is called
	 */
	public EntityRoute(String path, HttpMethod httpMethod,
					   TriConsumer<Request, Response, T> action, Class<T> clazz) {
		super(path, httpMethod);
		this.action = action;
		this.requestBodyClass = clazz;
	}

	@Override
	public EntityRoute consumes(MediaType... consumes) {
		super.consumes(consumes);
		return this;
	}

	@Override
	public EntityRoute produces(MediaType... produces) {
		super.produces(produces);
		return this;
	}

	@Override
	public boolean hasRequestBody() {
		return true;
	}

	@Override
	public Optional<Class<?>> getRequestBodyClass() {
		return Optional.ofNullable(requestBodyClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param request
	 */
	@Override
	public Response execute(Request request, Response response) {
		final Request immutableRequest = ImmutableRequest.of(request);
		action.accept(immutableRequest, response, requestBodyClass.cast(request.getEntity().get()));
		return response;
	}
}
