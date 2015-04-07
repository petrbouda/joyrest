package org.joyrest.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public final class JoyLogger {

	public final Logger logger;

	public JoyLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	public void error(Supplier<String> message) {
		if(logger.isErrorEnabled()) {
			logger.error(message.get());
		}
	}

	public void warn(Supplier<String> message) {
		if(logger.isWarnEnabled()) {
			logger.warn(message.get());
		}
	}

	public void info(Supplier<String> message) {
		if(logger.isInfoEnabled()) {
			logger.info(message.get());
		}
	}

	public void debug(Supplier<String> message) {
		if(logger.isDebugEnabled()) {
			logger.debug(message.get());
		}
	}

	public void trace(Supplier<String> message) {
		if(logger.isTraceEnabled()) {
			logger.trace(message.get());
		}
	}

}
