package org.joyrest.transform;

import org.joyrest.common.annotation.General;
import org.joyrest.model.http.MediaType;

public interface Transformer extends General {

	MediaType getMediaType();

}
