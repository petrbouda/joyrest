package org.joyrest.ittest;

import java.io.IOException;

import org.joyrest.exception.AbstractExceptionConfiguration;

public class TestExceptionConfiguration extends AbstractExceptionConfiguration {

	@Override
	protected void configure() {

		/*
		 * exception((IOException ex, JoyRequest req, JoyResponse resp) -> {
		 * 
		 * });
		 */

		exception(IOException.class, (ex, req, resp) -> {

		});
	}
}
