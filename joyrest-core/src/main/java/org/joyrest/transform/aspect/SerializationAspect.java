package org.joyrest.transform.aspect;

import static org.joyrest.exception.type.RestException.notAcceptableSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class SerializationAspect implements Aspect {

	@Override
	public InternalResponse<?> around(AspectChain chain, InternalRequest request, InternalResponse response) {
		EntityRoute route = chain.getRoute();
		if (route.hasRequestBody()) {
			Object entity = readEntity(route, request);
			request.setEntity(entity);
		}

		chain.proceed(request, response);
		writeEntity(route, request, response);
		return response;
	}

	private void writeEntity(EntityRoute route, InternalRequest<?> request, InternalResponse<?> response) {
		if (response.getEntity().isPresent()) {
			MediaType accept = request.getMatchedAccept();
			response.header(HeaderName.CONTENT_TYPE, accept.get());
			Writer writer = route.getWriter(accept)
				.orElseThrow(notAcceptableSupplier());
			writer.writeTo(response);
		}
	}

	private Object readEntity(EntityRoute route, InternalRequest<Object> request) {
		MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
		Reader reader = route.getReader(contentType)
			.orElseThrow(unsupportedMediaTypeSupplier());
		return reader.readFrom(request, route.getRequestType());
	}

}
