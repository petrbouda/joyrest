package org.joyrest.transform;

import java.io.*;
import java.io.Writer;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

public class StringReaderWriter extends AbstractReaderWriter {

	private final MediaType supportedMediaType = MediaType.PLAIN_TEXT;

	private final String DEFAULT_CHARSET = "UTF-8";

	@Override
	public <T> T readFrom(InternalRequest<T> request, Type<T> clazz) {
		try {
			String charset = request.getMatchedAccept().getParam("charset").orElse(DEFAULT_CHARSET);
			StringBuilder builder = new BufferedReader(
					new InputStreamReader(request.getInputStream(), charset)).lines()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
			return (T) builder.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Unsupported Encoding Exception");
		}
	}

	@Override
	public void writeTo(InternalResponse<?> response) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			writer.append((CharSequence) response.getEntity().get());
			writer.flush();
		} catch (UnsupportedEncodingException e) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Unsupported Encoding Exception");
		} catch (IOException e) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "IO Exception occurred during writing from String");
		}
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
