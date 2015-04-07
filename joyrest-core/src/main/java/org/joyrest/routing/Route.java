package org.joyrest.routing;

import org.joyrest.aspect.Aspect;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.transform.Reader;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Container for all information about one route {@link AbstractRoute}
 *
 * @author pbouda
 */
public interface Route {

    /**
     * Executes the store action of given route and generates {@link LambdaResponse}
     * for clients.
     *
     * @param request {@code request} dedicated to the concrete route
     * @return generated response that will be propagated to the client
     */
    Response execute(Request request, Response response);

	List<Aspect> getAspects();

    HttpMethod getHttpMethod();

    List<RoutePart<?>> getRouteParts();

    Map<String, RoutePart<?>> getPathParams();

    List<MediaType> getConsumes();

    List<MediaType> getProduces();

    boolean hasRequestBody();

    Optional<Class<?>> getRequestBodyClass();

    /**
     * Returns concrete reader registered belongs to the route
     *
     * @return {@link Reader} according to the given media type
     */
    Optional<Reader> getReader(MediaType mediaType);

    /**
     * Registers all readers belongs to the route
     *
     * @param readers {@link Map} with collection of {@code readers}
     */
    void setReaders(Map<MediaType, Reader> readers);


}
