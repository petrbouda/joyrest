package org.joyrest.transform.aspect;

import org.joyrest.aspect.Aspect;
import org.joyrest.aspect.AspectChain;
import org.joyrest.collection.DefaultMultiMap;
import org.joyrest.context.ApplicationContext;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.request.Request;
import org.joyrest.model.response.Response;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.ReaderRegistrar;
import org.joyrest.transform.Writer;
import org.joyrest.transform.WriterRegistrar;
import org.joyrest.utils.SerializationUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.joyrest.exception.type.RestException.unsupportedMediaTypeSupplier;
import static org.joyrest.model.http.HeaderName.ACCEPT;
import static org.joyrest.model.http.HeaderName.CONTENT_TYPE;

public class SerializationAspect implements Aspect {

	private final DefaultMultiMap<MediaType, WriterRegistrar> writers;

	public SerializationAspect(DefaultMultiMap<MediaType, WriterRegistrar> writers) {
		this.writers = writers;
	}

	@Override
	public Response around(final AspectChain chain, final Request request, final Response response) {
		InternalRequest internalRequest = (InternalRequest) request;

		Route route = chain.getRoute();
		if (route instanceof EntityRoute) {
			Object entity = readEntity(route, internalRequest);
			internalRequest.setEntity(entity);
		}

		chain.proceed(request, response);
		writeEntity(request, response);
		return response;
	}

	private void writeEntity(final Request request, final Response response) {
		if(response.getEntity().isPresent()) {
			MediaType accept = request.getHeader(ACCEPT).map(MediaType::of).get();
			Class<?> responseClass = response.getEntity().get().getClass();
			Writer writer = SerializationUtils.selectWriter(writers, responseClass, accept);
			writer.writeTo(response);
		}
	}

	private Object readEntity(final Route route, final InternalRequest request) {
		MediaType contentType = request.getHeader(CONTENT_TYPE).map(MediaType::of).get();
		Reader reader = route.getReader(contentType)
			.orElseThrow(unsupportedMediaTypeSupplier());
		EntityRoute<?> entityRoute = (EntityRoute<?>) route;
		return reader.readFrom(request, entityRoute.getRequestBodyClass().get());
	}

}
