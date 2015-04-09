package org.joyrest.transform.aspect;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.transform.WriterRegistrar;
import org.joyrest.utils.SerializationUtils;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

public class SerializationAspect<REQ, RESP> implements Aspect<REQ, RESP> {

	private final DefaultMultiMap<MediaType, WriterRegistrar> writers;

	public SerializationAspect(DefaultMultiMap<MediaType, WriterRegistrar> writers) {
		this.writers = writers;
	}

	@Override
	public InternalResponse<RESP> around(final AspectChain<REQ, RESP> chain,
										 final InternalRequest<REQ> request, final InternalResponse<RESP> response) {
		Route<REQ, RESP> route = chain.getRoute();
		if (route instanceof EntityRoute) {
			REQ entity = readEntity(route, request);
			request.setEntity(entity);
		}

		chain.proceed(request, response);
		writeEntity(request, response);
		return response;
	}

	@SuppressWarnings("unchecked")
	private void writeEntity(final InternalRequest<REQ> request, final InternalResponse<RESP> response) {
		if(response.getEntity().isPresent()) {
			MediaType accept = request.getHeader(ACCEPT).map(MediaType::of).get();
			Class<?> responseClass = response.getEntity().get().getClass();
			Writer<RESP> writer = SerializationUtils.selectWriter(writers, (Class<RESP>) responseClass, accept);
			writer.writeTo(response);
		}
	}

	private REQ readEntity(final Route<REQ, RESP> route, final InternalRequest<REQ> request) {
		MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
		Reader<REQ> reader = route.getReader(contentType)
			.orElseThrow(unsupportedMediaTypeSupplier());
		EntityRoute<REQ, RESP> entityRoute = (EntityRoute<REQ, RESP>) route;
		return reader.readFrom(request, entityRoute.getRequestBodyClass());
	}

}
