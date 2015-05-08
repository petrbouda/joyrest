package org.joyrest.transform;

import java.io.*;
import java.io.Writer;
import java.util.Objects;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;

public class StringReaderWriter extends AbstractReaderWriter {

	private final MediaType supportedMediaType = MediaType.PLAIN_TEXT;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readFrom(InternalRequest<T> request, Type<T> clazz) {
		try {
			String charset = request.getHeader(HeaderName.CONTENT_TYPE)
				.filter(Objects::nonNull)
				.map(MediaType::of)
				.flatMap(mediaType -> mediaType.getParam("charset"))
				.orElse(DEFAULT_CHARSET);

			StringBuilder builder = new BufferedReader(
				new InputStreamReader(request.getInputStream(), charset)).lines()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
			return (T) builder.toString();
		} catch (UnsupportedEncodingException e) {
			throw internalServerErrorSupplier("Unsupported Encoding Exception").get();
		}
	}

	@Override
	public void writeTo(InternalResponse<?> response, InternalRequest<?> request) {
		try {
			String charset = request.getMatchedAccept().getParam("charset")
				.orElse(DEFAULT_CHARSET);

			Writer writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), charset));
			writer.append((CharSequence) response.getEntity().get());
			writer.flush();
		} catch (UnsupportedEncodingException e) {
			throw internalServerErrorSupplier("Unsupported Encoding Exception").get();
		} catch (IOException e) {
			throw internalServerErrorSupplier("IO Exception occurred during writing from String").get();
		}
	}

	@Override
	public boolean isClassCompatible(Class<?> clazz) {
		return true;
	}

	@Override
	public MediaType getMediaType() {
		return supportedMediaType;
	}
}
