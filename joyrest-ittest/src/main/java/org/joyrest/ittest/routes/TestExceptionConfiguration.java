package org.joyrest.ittest.routes;

import org.joyrest.exception.AbstractExceptionConfiguration;

import java.io.IOException;

public class TestExceptionConfiguration extends AbstractExceptionConfiguration {

	@Override
	protected void configure() {

		/*exception((IOException ex, JoyRequest req, JoyResponse resp) -> {

		});*/

		exception(IOException.class, (ex, req, resp) -> {

		});
	}
}
