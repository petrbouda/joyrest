package org.joyrest.routing;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.Type;
import org.joyrest.utils.PathUtils;

/**
 * Class {@link AbstractControllerConfiguration} is abstract implementation of {@link ControllerConfiguration} and makes easier to create
 * the given route using predefined protected method.
 * <p/>
 * It can be considered as container for routes which are provided to {@link RequestProcessor} because of processing and handling incoming
 * requests.
 *
 * @author pbouda
 */
public abstract class AbstractControllerConfiguration implements ControllerConfiguration {

	/* Set of routes which are configured in an inherited class */
	private final Set<EntityRoute> routes = new HashSet<>();

	/* Class validates and customized given path */
	private final PathCorrector pathCorrector = new PathCorrector();

	/* Resource path that will be added to the beginning of all routes defined in the inherited class */
	private String globalPath = null;

	/* RoutingConfiguration's initialization should be executed only once */
	private boolean isInitialized = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void initialize() {
		if (!this.isInitialized) {
			configure();

			List<RoutePart<String>> globalParts = PathUtils.createRouteParts(globalPath);

			if (globalPath != null) {
				this.routes.stream().forEach(route -> route.addGlobalPath(globalParts));
			}

			this.isInitialized = true;
		}
	}

	/**
	 * Convenient place where is possible to configure new routes for this instance of {@link ControllerConfiguration}
	 */
	abstract protected void configure();

	/**
	 * Creates a resource part of the path unified for all routes defined in the inherited class
	 *
	 * @param path resource path of all defined class
	 * @throws NullPointerException whether {@code path} is {@code null}
	 */
	protected final void setGlobalPath(String path) {
		requireNonNull(path, "Global path cannot be change to 'null'");

		if (!"".equals(path) || !"/".equals(path)) {
			this.globalPath = pathCorrector.apply(path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<EntityRoute> getRoutes() {
		return routes;
	}

	protected <REQ, RESP> EntityRoute createEntityRoute(HttpMethod method, String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Type<REQ> reqClazz, Type<RESP> respClazz) {
		final String correctPath = pathCorrector.apply(path);
		final EntityRoute route = new EntityRoute(correctPath, method, action, reqClazz, respClazz);
		routes.add(route);
		return route;
	}
}