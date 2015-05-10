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

/**
 * Implementation of this interface is able to configure JoyREST framework for specified dependency injection framework.
 *
 * @param <T> type of the configuration class that is specialized according to dependency injection framework
 *
 * @see DependencyInjectionConfigurer
 * @see NonDiConfigurer
 *
 * @author pbouda
 */
public interface Configurer<T> {

	/**
	 * Initializes a given dependency injection framework
	 *
	 * @param applicationConfig class with definition of the custom dependencies
	 * @return instance with all needed information for a successful running of the application
	 */
	ApplicationContext initialize(T applicationConfig);

}
