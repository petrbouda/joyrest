package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.response.Response;
import org.joyrest.routing.Route;

import java.util.function.Predicate;

public interface Writer extends Predicate<Route>, Default, Transformer {

	void writeTo(Response response);

}
