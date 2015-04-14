package org.joyrest.routing;

import java.util.function.BiConsumer;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.*;

/**
 * Class {@link TypedControllerConfiguration} is abstract implementation of {@link ControllerConfiguration} and makes easier to create the
 * given route using predefined protected method.
 * <p/>
 * It can be considered as container for routes which are provided to {@link RequestProcessor} because of processing and handling incoming
 * requests.
 *
 * @author pbouda
 */
public abstract class TypedControllerConfiguration extends AbstractControllerConfiguration {

	/* --------------- POST ROUTES --------------- */

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
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
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> post(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
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

	/* ---------------- GET ROUTES ---------------- */

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.GET, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> get(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.GET, path, action, null, null);
	}

	/* ---------------- PUT ROUTES ---------------- */

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, new Type<>(req), resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, Class<REQ> req, Class<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, new Type<>(req), new Type<>(resp));
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, resp);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> put(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, null);
	}

	/* ------------------ DELETE ROUTES ------------------ */

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.DELETE, "", action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action) {
		return createEntityRoute(HttpMethod.DELETE, path, action, null, null);
	}

	protected final <REQ, RESP> EntityRoute<REQ, RESP> delete(String path,
			BiConsumer<Request<REQ>, Response<RESP>> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.DELETE, path, action, req, null);
	}

}
