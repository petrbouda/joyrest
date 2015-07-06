/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.dagger.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public interface MainConfigurationTemplate extends ConfigurationTemplate {

	Set<Writer> writers();

	Set<Reader> readers();

	Set<Interceptor> interceptors();

	Set<ControllerConfiguration> controllerConfigurations();

	Set<ExceptionConfiguration> exceptionConfigurations();

	@Override
	default Map<Class<?>, Supplier<Set<?>>> getSuppliers() {
		HashMap<Class<?>, Supplier<Set<?>>> beanFactory = new HashMap<>();
		beanFactory.put(Writer.class, this::writers);
		beanFactory.put(Reader.class, this::readers);
		beanFactory.put(Interceptor.class, this::interceptors);
		beanFactory.put(ControllerConfiguration.class, this::controllerConfigurations);
		beanFactory.put(ExceptionConfiguration.class, this::exceptionConfigurations);
		return beanFactory;
	}

}
