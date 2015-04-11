package org.joyrest.transform.aspect;

import static org.joyrest.exception.type.RestException.notAcceptableSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class SerializationAspect<REQ, RESP> implements Aspect<REQ, RESP> {

	@Override
	public InternalResponse<RESP> around(final AspectChain<REQ, RESP> chain,
			final InternalRequest<REQ> request, final InternalResponse<RESP> response) {
		EntityRoute<REQ, RESP> route = chain.getRoute();
		if (route.hasRequestBody()) {
			REQ entity = readEntity(route, request);
			request.setEntity(entity);
		}

		chain.proceed(request, response);
		writeEntity(route, request, response);
		return response;
	}

	private void writeEntity(final Route<REQ, RESP> route, final InternalRequest<REQ> request, final InternalResponse<RESP> response) {
		if (response.getEntity().isPresent()) {
			MediaType accept = request.getHeader(ACCEPT).map(MediaType::of).get();
			Writer<RESP> writer = route.getWriter(accept)
				.orElseThrow(notAcceptableSupplier());
			writer.writeTo(response);
		}
	}

	private REQ readEntity(final EntityRoute<REQ, RESP> route, final InternalRequest<REQ> request) {
		MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
		Reader<REQ> reader = route.getReader(contentType)
			.orElseThrow(unsupportedMediaTypeSupplier());
		return reader.readFrom(request, route.getRequestType());
	}

}
