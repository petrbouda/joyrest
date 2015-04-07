package org.joyrest.routing;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Container for all information about one route {@link SimpleRoute} that does not contain information about body
 * <p>
 * As a child of the {@link AbstractRoute} is able to execute a stored action
 * {@link SimpleRoute#execute(Request, Response)}
 * </p>
 *
 * @author pbouda
 */
public class SimpleRoute extends AbstractRoute {

    /* route action which contains information about a request body */
    private final BiConsumer<Request, Response> action;

    /**
     * @param path       entire path of the route
     * @param httpMethod http method which belongs to this route
     * @param action     action what will be executed if the route is called
     */
    public SimpleRoute(String path, HttpMethod httpMethod, BiConsumer<Request, Response> action) {
        super(path, httpMethod);
        this.action = action;
    }

    @Override
    public SimpleRoute produces(MediaType... produces) {
        super.produces(produces);
        return this;
    }


    @Override
    public boolean hasRequestBody() {
        return false;
    }

    @Override
    public Optional<Class<?>> getRequestBodyClass() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     *
     * @param request
     */
    @Override
    public Response execute(final Request request, Response response) {
        action.accept(request, response);
        return response;
    }

}
