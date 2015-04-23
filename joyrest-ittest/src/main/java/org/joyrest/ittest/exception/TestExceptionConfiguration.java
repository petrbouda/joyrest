package org.joyrest.ittest.exception;

import static org.joyrest.routing.entity.ResponseType.Resp;

import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.ittest.entity.FeedEntry;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;

import java.util.Date;

public class TestExceptionConfiguration extends TypedExceptionConfiguration {

	@Override
	protected void configure() {

		handle(NumberFormatException.class, (req, resp, ex) -> {
			FeedEntry entry = new FeedEntry();
			entry.setLink("http://localhost:8080");
			entry.setPublishDate(new Date());
			entry.setTitle("My Feed Title");
			entry.setDescription("My Feed Description");

			resp.status(HttpStatus.BAD_REQUEST);
			resp.header(HeaderName.of("x-special-header"), "SPECIAL");
			resp.entity(entry);
		}, Resp(FeedEntry.class));

	}
}
