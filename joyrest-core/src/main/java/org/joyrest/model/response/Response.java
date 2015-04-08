package org.joyrest.model.response;

import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

public interface Response<E> {

	Response<E> header(HeaderName name, String value);

	Response<E> status(HttpStatus status);

	Response<E> entity(E entity);

}