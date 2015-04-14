package org.joyrest.routing;

import org.joyrest.aspect.Aspect;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.entity.Type;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Container for all information about one route {@link EntityRoute}
 *
 * @author pbouda
 */
public interface Route<REQ, RESP> {

    InternalResponse<RESP> execute(InternalRequest<REQ> request, InternalResponse<RESP> response);

	List<Aspect<REQ, RESP>> getAspects();

    HttpMethod getHttpMethod();

    List<RoutePart<?>> getRouteParts();

    String getPath();

    Map<String, RoutePart<?>> getPathParams();

    List<MediaType> getConsumes();

    List<MediaType> getProduces();

    Type<REQ> getRequestType();

    Type<RESP> getResponseType();

    Optional<Reader> getReader(MediaType mediaType);

    Optional<Writer> getWriter(MediaType mediaType);

}
