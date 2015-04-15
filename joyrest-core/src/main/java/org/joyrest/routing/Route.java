package org.joyrest.routing;

import org.joyrest.aspect.Aspect;
import org.joyrest.model.http.MediaType;

public interface Route {

	Route aspect(Aspect... aspect);

	Route consumes(MediaType... consumes);

	Route produces(MediaType... produces);

}
