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

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.ApplicationContextImpl;
import org.joyrest.context.autoconfigurar.AutoConfigurer;
import org.joyrest.context.initializer.BeanFactory;
import org.joyrest.context.initializer.InitContext;
import org.joyrest.context.initializer.Initializer;
import org.joyrest.context.initializer.MainInitializer;

/**
 * Abstract class as a helper for initialization an {@link ApplicationContext}.
 *
 * @param <T> type of configurer class which is used to set up a configurer
 * @author pbouda
 * @see Configurer
 * @see NonDiConfigurer
 */
public abstract class AbstractConfigurer<T> implements Configurer<T> {

	/**
	 * Method causes the initialization of the application context using the methods which returns a collection of beans such as
	 *
	 * @return initialized {@code application context}
	 */
	protected ApplicationContext initializeContext() {
		Function<Class<Object>, List<Object>> getBeans = this::getBeans;

		BeanFactory beanFactory = new BeanFactory(getBeans);
		InitContext context = new InitContext();

		AutoConfigurer.configureInitializers()
			.forEach(initializer -> initializer.init(context, beanFactory));

		MainInitializer mainInitializer = new MainInitializer();
		mainInitializer.init(context, beanFactory);

		ApplicationContextImpl applicationContext = new ApplicationContextImpl();
		applicationContext.setRoutes(context.getRoutes());
		applicationContext.setExceptionHandlers(context.getExceptionHandlers());
		return applicationContext;
	}

}
