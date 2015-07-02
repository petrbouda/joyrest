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
package org.joyrest.context.configurer;

import static java.util.Objects.requireNonNull;
import java.util.Collection;

import org.joyrest.context.ApplicationContext;

/**
 * Configurer which is mainly used for an implementation of a new configurer that is not based on any Dependency Injection
 * Framework.
 *
 * {@inheritDoc}
 */
public class NonDiConfigurer extends AbstractConfigurer<ConfigurationContext> {

	private ConfigurationContext context;

	@Override
	@SuppressWarnings("unchecked")
	public <B> Collection<B> getBeans(Class<B> clazz) {
		return (Collection<B>) context.get(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationContext initialize(ConfigurationContext context) {
		requireNonNull(context, "ApplicationContext cannot be null.");
		this.context = context;
		return initializeContext();
	}

}
