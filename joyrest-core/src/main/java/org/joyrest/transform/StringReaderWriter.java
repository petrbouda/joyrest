package org.joyrest.transform;

import java.io.*;
import java.io.Writer;

import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.*;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.entity.Type;

public class StringReaderWriter extends AbstractReaderWriter {

    private final MediaType supportedMediaType = MediaType.PLAIN_TEXT;

    @Override
    public String readFrom(InternalRequest<String> request, Type<String> clazz) {
        try {
            StringBuilder builder = new BufferedReader(
                    new InputStreamReader(request.getInputStream(), "UTF-8")).lines()
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
            return builder.toString()
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
