package org.joyrest.ittest;

import static org.joyrest.routing.entity.RequestType.Req;
import static org.joyrest.routing.entity.ResponseType.Resp;

import org.joyrest.exception.TypedExceptionConfiguration;
import org.joyrest.ittest.entity.FeedEntry;

public class TestExceptionConfiguration extends TypedExceptionConfiguration {

	@Override
	protected void configure() {

		exception(NumberFormatException.class, (req, resp, ex) -> {

		}, Req(FeedEntry.class), Resp(FeedEntry.class));

	}

}
