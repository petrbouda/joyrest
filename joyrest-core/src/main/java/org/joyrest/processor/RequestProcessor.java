package org.joyrest.processor;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.model.response.Response;

/**
 * An interface of a processor which is able to generate a correct {@link Response} thank to information provided from the
 * {@link InternalRequest} object.
 *
 * @author pbouda
 */
@FunctionalInterface
public interface RequestProcessor {

	/**
	 * Generates correct {@link Response response} on the base of the incoming {@link InternalRequest request} as a abstraction of any
	 * server data.
	 *
	 * @param request incoming request in the form of {@link InternalRequest}
	 * @param response response in which is stored a processing result
	 * @return generated response on the base of the incoming request
	 * @throws java.lang.Exception
	 */
	InternalResponse<?> process(InternalRequest<?> request, InternalResponse<?> response) throws Exception;

}
