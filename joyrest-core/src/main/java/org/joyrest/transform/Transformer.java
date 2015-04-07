package org.joyrest.transform;

import org.joyrest.collection.annotation.Default;
import org.joyrest.model.http.MediaType;

public interface Transformer extends Default {

	MediaType[] getMediaTypes();

}
