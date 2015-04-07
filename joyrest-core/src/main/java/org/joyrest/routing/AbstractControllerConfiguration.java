package org.joyrest.routing;

import net.jodah.typetools.TypeResolver;
import org.joyrest.function.TriConsumer;
import org.joyrest.model.RoutePart;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.utils.PathUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * Class {@link AbstractControllerConfiguration} is abstract implementation of
 * {@link ControllerConfiguration} and makes easier to create the given route
 * using predefined protected method.
 * <p/>
 * It can be considered as container for routes which are provided to
 * {@link RequestProcessor} because of processing
 * and handling incoming requests.
 *
 * @author pbouda
 */
public abstract class AbstractControllerConfiguration implements ControllerConfiguration {

	/* Set of routes which are configured in an inherited class  */
	private final Set<AbstractRoute> routes = new HashSet<>();

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

	/*
	* ----------------
    * 	POST ROUTES
    * ----------------
    **/

	/**
	 * Creates a route with POST method and the given path and action
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute post(String path, BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.POST, path, action);
	}

	/**
	 * Creates a route with POST method and the given path and action
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute post(BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.POST, "", action);
	}

	/**
	 * Creates a route with POST method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> post(String path,
											TriConsumer<Request, Response, T> action, Class<T> clazz) {
		return createEntityRoute(HttpMethod.POST, path, action, clazz);
	}

	/**
	 * Creates a route with POST method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> post(String path, TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.POST, path, action, getActionBodyClass(action));
	}

	/**
	 * Creates a route with POST method and the given path and an action with a body type
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> post(TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.POST, "", action, getActionBodyClass(action));
	}

	/**
	 * Creates a route with POST method and the base path and an action with a body type
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> post(TriConsumer<Request, Response, T> action, Class<T> clazz) {
		return createEntityRoute(HttpMethod.POST, "", action, clazz);
	}

	/*
	* ----------------
    * 	GET ROUTES
    * ----------------
    **/

	/**
	 * Creates a route with GET method and the given path and action
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute get(String path, BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.GET, path, action);
	}

	/**
	 * Creates a route with GET method and the given action
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute get(BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.GET, "", action);
	}

	/**
	 * Creates a route with GET method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> get(String path,
										   TriConsumer<Request, Response, T> action, Class<T> clazz) {
		return createEntityRoute(HttpMethod.GET, path, action, clazz);
	}

	/**
	 * Creates a route with GET method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> get(String path, TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.GET, path, action, getActionBodyClass(action));
	}

	/**
	 * Creates a route with GET method and the given path and an action with a body type
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> get(TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.GET, "", action, getActionBodyClass(action));
	}

    /*
	* ----------------
    * 	PUT ROUTES
    * ----------------
    **/

	/**
	 * Creates a route with PUT method and the given path and action
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute put(String path, BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.PUT, path, action);
	}

	/**
	 * Creates a route with PUT method and the given path and action
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute put(BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.PUT, "", action);
	}

	/**
	 * Creates a route with PUT method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> put(String path,
										   TriConsumer<Request, Response, T> action, Class<T> clazz) {
		return createEntityRoute(HttpMethod.PUT, path, action, clazz);
	}

	/**
	 * Creates a route with PUT method and the given path and an action with a body type
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> put(String path, TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.PUT, path, action, getActionBodyClass(action));
	}

	/**
	 * Creates a route with PUT method and the given path and an action with a body type
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final <T> EntityRoute<T> put(TriConsumer<Request, Response, T> action) {
		return createEntityRoute(HttpMethod.PUT, "", action, getActionBodyClass(action));
	}

	/*
	* ------------------
    * 	DELETE ROUTES
    * ------------------
    **/

	/**
	 * Creates a route with DELETE method and the given path and action
	 *
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute delete(BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.DELETE, "", action);
	}

	/**
	 * Creates a route with DELETE method and the given path and action
	 *
	 * @param path   path which corresponds to this route
	 * @param action action which will be executed if the route is selected
	 */
	protected final SimpleRoute delete(String path, BiConsumer<Request, Response> action) {
		return createSimpleRoute(HttpMethod.DELETE, path, action);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<AbstractRoute> getRoutes() {
		return routes;
	}

	private SimpleRoute createSimpleRoute(HttpMethod method, String path, BiConsumer<Request, Response> action) {
		final String correctPath = pathCorrector.apply(path);
		final SimpleRoute route = new SimpleRoute(correctPath, method, action);
		routes.add(route);
		return route;
	}

	private <T> EntityRoute<T> createEntityRoute(HttpMethod method, String path,
												 TriConsumer<Request, Response, T> action, Class<T> clazz) {
		final String correctPath = pathCorrector.apply(path);
		final EntityRoute<T> route = new EntityRoute<>(correctPath, method, action, clazz);
		routes.add(route);
		return route;
	}

	/**
	 * Method returns third argument's class type of {@link TriConsumer} which is added runtime
	 * during defining custom controllers.
	 *
	 * @param action action defined in a controller
	 * @param <T>    body type
	 * @return {@link java.lang.Class} which corresponds to the body of the route
	 */
	@SuppressWarnings("unchecked")
	private <T> Class<T> getActionBodyClass(TriConsumer<Request, Response, T> action) {
		return (Class<T>) TypeResolver.resolveRawArguments(TriConsumer.class, action.getClass())[2];
	}
}
