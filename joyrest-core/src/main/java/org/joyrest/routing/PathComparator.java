package org.joyrest.routing;

import com.codepoetics.protonpack.StreamUtils;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.RoutePart;
import org.joyrest.model.request.InternalRequest;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * Class is able compare the equality of a configured route's {@link Route} path and the path
 * from the incoming request.
 *
 * @author pbouda
 */
public class PathComparator implements BiPredicate<EntityRoute<?, ?>, InternalRequest<?>> {

    /**
     * Compares a configured route with the given path which comes from the client call.
     *
     * @param route configured route
     * @param request incoming request
     * @return returns true if all parts are equal
     **/
    @Override
    public boolean test(EntityRoute<?, ?> route, InternalRequest<?> request) {
        List<String> pathParts = request.getPathParts();
        List<RoutePart<?>> routeParts = route.getRouteParts();
        if(routeParts.size() != pathParts.size()){
            return false;
        }

        return StreamUtils.zip(routeParts.stream(), pathParts.stream(), PathComparator::compareParts)
                .allMatch(result -> result);
    }

    /**
     * Compares the route part (part which is configured) and the path part
     * (part which is gained from the client).
     *
     * <p>If it is just string path, so this method will compare the value of the strings.<p/>
     * <p>If it is param path, so method will find out whether is possible to cast the object
     * or not.<p/>
     *
     * @param routePart configured part
     * @param pathPart path from a client's call
     * @return returns true if the parts are equal
     * @throws RestException is not possible to cast the param type
     **/
    private static boolean compareParts(RoutePart<?> routePart, String pathPart) {
        switch(routePart.getType()) {
            case PATH:
                return routePart.getValue().equals(pathPart);
            case PARAM:
                return routePart.getPathType().isAssignableFromString(pathPart);
            default:
                return false;
        }
    }
}
