package org.joyrest.transform;

import org.joyrest.model.http.MediaType;
import org.joyrest.routing.Route;

public interface Transformer {

	boolean isCompatible(Route<?, ?> route);

	MediaType getMediaType();

}
