package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.Route;

import java.util.function.Predicate;

public interface Reader extends Predicate<Route>, Default, Transformer {

	<T> T readFrom(InternalRequest request, Class<T> clazz);

}
