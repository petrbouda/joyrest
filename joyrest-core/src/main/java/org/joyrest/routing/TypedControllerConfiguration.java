package org.joyrest.routing;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.*;

import java.util.function.BiConsumer;

/**
 * Class {@link TypedControllerConfiguration} is abstract implementation of
 * {@link ControllerConfiguration} and makes easier to create the given route
 * using predefined protected method.
 * <p/>
 * It can be considered as container for routes which are provided to
 * {@link RequestProcessor} because of processing
 * and handling incoming requests.
 *
 * @author pbouda
 */
public abstract class TypedControllerConfiguration extends AbstractControllerConfiguration {

//	---------------
//    POST ROUTES
//  ---------------

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, RequestGenericType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action, ResponseGenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.POST, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.POST, path, action, null, null);
	}

	/*
	* ----------------
    * 	GET ROUTES
    * ----------------
    **/

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		RequestGenericType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, (Class<REQ>) req.getType(), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		ParamType<REQ> req, ParamType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action,
			(Class<REQ>) req.getType(), (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, ParamType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.GET, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.GET, "", action, null, null);
	}

    /*
	* ----------------
    * 	PUT ROUTES
    * ----------------
    **/

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, (Class<REQ>) req.getType(), resp);
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		GenericType<REQ> req, GenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action,
			(Class<REQ>) req.getType(), (Class<RESP>) resp.getType());
	}

	@SuppressWarnings("unchecked")
	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, GenericType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, (Class<RESP>) resp.getType());
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action,
		Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}


	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, null);
	}

	/*
	* ------------------
    * 	DELETE ROUTES
    * ------------------
    **/

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.DELETE, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(String path,
		BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.DELETE, path, action, null, null);
	}

}
