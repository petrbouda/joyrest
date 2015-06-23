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
package org.joyrest.routing;

import java.util.Set;

/**
 * A basic configurer interface contains method {@link #initialize()} in which is mostly set of {@link InternalRoute}
 * classes represent an individual handler for client's model.
 *
 * @author pbouda
 **/
public interface ControllerConfiguration {

	/**
	 * Method contains a configurer of {@link Route} classes and other objects which are in a connection with these routes.
	 *
	 * This method is considered as a entry point for configurer of routes.
	 **/
	void initialize();

	/**
	 * Provides set routes configured in {@link #initialize()} method of the class implementing this interface.
	 *
	 * @return set of routes which are configured in {@link #initialize()} method
	 **/
	Set<InternalRoute> getRoutes();

}
