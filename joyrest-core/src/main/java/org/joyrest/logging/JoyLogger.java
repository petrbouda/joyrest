/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.logging;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JoyLogger {

	public final Logger logger;

	public static final JoyLogger of(Class<?> clazz) {
		return new JoyLogger(clazz);
	}

	public JoyLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	public void error(Supplier<String> message) {
		if (logger.isErrorEnabled()) {
			logger.error(message.get());
		}
	}

	public void warn(Supplier<String> message) {
		if (logger.isWarnEnabled()) {
			logger.warn(message.get());
		}
	}

	public void warnException(Supplier<Exception> message) {
		if (logger.isWarnEnabled()) {
			logger.warn("", message.get());
		}
	}

	public void info(Supplier<String> message) {
		if (logger.isInfoEnabled()) {
			logger.info(message.get());
		}
	}

	public void debug(Supplier<String> message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message.get());
		}
	}

	public void trace(Supplier<String> message) {
		if (logger.isTraceEnabled()) {
			logger.trace(message.get());
		}
	}

}
