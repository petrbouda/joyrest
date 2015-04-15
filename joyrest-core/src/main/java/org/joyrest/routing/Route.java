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

public interface Route {

    Route aspect(Aspect... aspect);

    Route consumes(MediaType... consumes);

    Route produces(MediaType... produces);

}
