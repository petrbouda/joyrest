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
package org.joyrest.context;

import java.util.Collection;

import org.joyrest.aspect.Aspect;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

/**
 * Abstract Configurer which is mainly used for an implementation of a new configurer that is based on some
 * Dependency Injection Framework.
 *
 * {@inheritDoc}
 */
public abstract class DependencyInjectionConfigurer<T> extends AbstractConfigurer<T> {

	/**
	 * Method which is used to retrieve a collection of beans from some dependency injection context according to a class of
	 * retrieved beans.
	 *
	 * @param clazz class that determined which beans will be retrieved
	 * @param <B> type of the retrieved beans
	 * @return collection of the retrieved beans according to the given class
	 */
	protected abstract <B> Collection<B> getBeans(Class<B> clazz);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Aspect> getAspects() {
		return getBeans(Aspect.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Reader> getReaders() {
		return getBeans(Reader.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Writer> getWriters() {
		return getBeans(Writer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<ExceptionConfiguration> getExceptionConfigurations() {
		return getBeans(ExceptionConfiguration.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<ControllerConfiguration> getControllerConfiguration() {
		return getBeans(ControllerConfiguration.class);
	}

}
