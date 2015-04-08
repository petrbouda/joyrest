package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;

import java.util.function.Predicate;

public interface Reader<T> extends Predicate<Route<T, ?>>, Default, Transformer {

	T readFrom(InternalRequest<T> request, Class<T> clazz);

}
