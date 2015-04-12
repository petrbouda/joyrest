package org.joyrest.transform;

import org.joyrest.common.annotation.Default;
import org.joyrest.model.response.InternalResponse;

public interface Writer extends Default, Transformer {

	void writeTo(InternalResponse<?> response);

}
