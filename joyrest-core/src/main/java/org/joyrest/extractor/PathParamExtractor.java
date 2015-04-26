package org.joyrest.extractor;

import java.util.function.BiFunction;

import org.joyrest.exception.type.RestException;
import org.joyrest.extractor.param.PathType;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.PathParam;

public class PathParamExtractor implements BiFunction<RoutePart<?>, String, PathParam> {

	/**
	 * Compares the route part (part which is configured) and the path part (part which is gained from the client) and tries to create a
	 * path param.
	 *
	 * <p>
	 * If it is just string path, so this method will return @{code null}</>.
	 * <p/>
	 * <p>
	 * If it is param path, so method will find out whether is possible to cast the object or not and then can throw an validation exception
	 * <p/>
	 *
	 * @param routePart configured part
	 * @param pathPart path from a client's call
	 * @return path param derived from route and incoming call or @{code null} whether the path part is
	 * @throws RestException is not possible to cast the param type
	 **/
	@Override
	public PathParam apply(RoutePart<?> routePart, String pathPart) {
		if (routePart.getType() == RoutePart.Type.PARAM)
			return new PathParam(routePart.getValue(), pathPart);

		return null;
	}

}
