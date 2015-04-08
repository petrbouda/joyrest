package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.Route;

import java.util.function.Predicate;

public interface Writer<T> extends Predicate<Route<?, T>>, Default, Transformer {

	void writeTo(InternalResponse<T> response);

}
