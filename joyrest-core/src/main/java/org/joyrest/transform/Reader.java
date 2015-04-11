package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;
import org.joyrest.routing.entity.Type;

import java.util.function.Predicate;

public interface Reader<T> extends Default, Transformer {

	T readFrom(InternalRequest<T> request, Type<T> clazz);

}
