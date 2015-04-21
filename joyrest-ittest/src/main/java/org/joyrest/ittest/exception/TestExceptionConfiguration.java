package org.joyrest.ittest.exception;

import static org.joyrest.routing.entity.ResponseType.Resp;

import org.joyrest.exception.TypedExceptionConfiguration;
import org.joyrest.ittest.entity.FeedEntry;

public class TestExceptionConfiguration extends TypedExceptionConfiguration {

	@Override
	protected void configure() {

		handle(NumberFormatException.class, (req, resp, ex) -> {

		}, Resp(FeedEntry.class));

	}

}
