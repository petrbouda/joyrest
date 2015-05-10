/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.routing;

import org.joyrest.model.http.HttpMethod;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.routing.entity.*;

/**
 * Class {@link TypedControllerConfiguration} is abstract implementation of {@link ControllerConfiguration} and makes easier to
 * create the  given route using predefined protected method.
 * <p/>
 * It can be considered as container for routes which are provided to {@link RequestProcessor} because of processing and handling
 * incoming requests.
 *
 * @see ControllerConfiguration
 * @see AbstractControllerConfiguration
 * @see Route
 * @author pbouda
 */
public abstract class TypedControllerConfiguration extends AbstractControllerConfiguration {

	/* --------------- POST ROUTES --------------- */

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, req, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, req, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, "", action, req, null);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, path, action, req, null);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, null, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, null, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, "", action, req, null);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.POST, path, action, req, null);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, "", action, null, resp);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.POST, path, action, null, resp);
	}

	protected final <REQ, RESP> Route post(RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.POST, "", action, null, null);
	}

	protected final <REQ, RESP> Route post(String path, RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.POST, path, action, null, null);
	}

	/* ---------------- GET ROUTES ---------------- */

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, req, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, req, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, "", action, req, null);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, path, action, req, null);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, null, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, null, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, "", action, req, null);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.GET, path, action, req, null);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, "", action, null, resp);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.GET, path, action, null, resp);
	}

	protected final <REQ, RESP> Route get(RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.GET, "", action, null, null);
	}

	protected final <REQ, RESP> Route get(String path, RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.GET, path, action, null, null);
	}

	/* ---------------- PUT ROUTES ---------------- */

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action,
			RequestCollectionType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action,
			RequestType<REQ> req, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, null);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, null);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action, ResponseType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, "", action, req, null);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action, RequestCollectionType<REQ> req) {
		return createEntityRoute(HttpMethod.PUT, path, action, req, null);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, resp);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action, ResponseCollectionType<RESP> resp) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, resp);
	}

	protected final <REQ, RESP> Route put(RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.PUT, "", action, null, null);
	}

	protected final <REQ, RESP> Route put(String path, RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.PUT, path, action, null, null);
	}

	/* ------------------ DELETE ROUTES ------------------ */

	protected final <REQ, RESP> Route delete(RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.DELETE, "", action, null, null);
	}

	protected final <REQ, RESP> Route delete(String path, RouteAction<REQ, RESP> action) {
		return createEntityRoute(HttpMethod.DELETE, path, action, null, null);
	}

	protected final <REQ, RESP> Route delete(String path, RouteAction<REQ, RESP> action, RequestType<REQ> req) {
		return createEntityRoute(HttpMethod.DELETE, path, action, req, null);
	}

}
