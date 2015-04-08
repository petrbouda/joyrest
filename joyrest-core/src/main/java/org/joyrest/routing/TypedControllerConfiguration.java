package org.joyrest.routing;

import org.joyrest.function.TriConsumer;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.GenericType;

import java.util.function.BiConsumer;

/**
 * Class {@link TypedControllerConfiguration} is abstract implementation of
 * {@link ControllerConfiguration} and makes easier to create the given route
 * using predefined protected method.
 * <p>
 * It can be considered as container for routes which are provided to
 * {@link RequestProcessor} because of processing
 * and handling incoming requests.
 *
 * @author pbouda
 */
public abstract class TypedControllerConfiguration extends AbstractControllerConfiguration {

	/*
	* ----------------
    * 	POST ROUTES
    * ----------------
    **/

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.POST, "", action, (Class<REQ>) req.getType(), resp);
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.POST, "", action,
			(Class<REQ>) req.getType(), (Class<RESP>) resp.getType());
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.POST, "", action, req, (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.POST, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.POST, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action, Class<REQ> clazz) {
		return createEntityRouteFromTri(HttpMethod.POST, path, action, clazz);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.POST, path, action, getActionBodyClass(action));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.POST, "", action, getActionBodyClass(action));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action, Class<REQ> clazz) {
		return createEntityRouteFromTri(HttpMethod.POST, "", action, clazz);
	}

	/*
	* ----------------
    * 	GET ROUTES
    * ----------------
    **/

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.GET, "", action, (Class<REQ>) req.getType(), resp);
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.GET, "", action,
			(Class<REQ>) req.getType(), (Class<RESP>) resp.getType());
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.GET, "", action, req, (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.GET, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.GET, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action, Class<REQ> clazz) {
		return createEntityRouteFromTri(HttpMethod.GET, path, action, clazz);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.GET, path, action, getActionBodyClass(action));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.GET, "", action, getActionBodyClass(action));
	}

    /*
	* ----------------
    * 	PUT ROUTES
    * ----------------
    **/

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.PUT, "", action, (Class<REQ>) req.getType(), resp);
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.PUT, "", action,
			(Class<REQ>) req.getType(), (Class<RESP>) resp.getType());
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, GenericType<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.PUT, "", action, req, (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, Class<RESP> resp) {
		return createEntityRouteFromBi(HttpMethod.PUT, "", action, req, resp);
	}


	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.PUT, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.PUT, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action, Class<REQ> clazz) {
		return createEntityRouteFromTri(HttpMethod.PUT, path, action, clazz);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
		TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.PUT, path, action, getActionBodyClass(action));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(TriConsumer<Request<REQ>, Response<RESP>, REQ> action) {
		return createEntityRouteFromTri(HttpMethod.PUT, "", action, getActionBodyClass(action));
	}

	/*
	* ------------------
    * 	DELETE ROUTES
    * ------------------
    **/

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.DELETE, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRouteFromBi(HttpMethod.DELETE, path, action, null, null);
	}

}
