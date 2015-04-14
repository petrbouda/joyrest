package org.joyrest.transform;

import org.joyrest.common.annotation.General;
import org.joyrest.model.response.InternalResponse;

public interface Writer extends General, Transformer {

	void writeTo(InternalResponse<?> response);

}
