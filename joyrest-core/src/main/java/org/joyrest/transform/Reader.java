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
package org.joyrest.transform;

import org.joyrest.model.request.InternalRequest;
import org.joyrest.routing.InternalRoute;
import org.joyrest.routing.entity.Type;

/**
 * Provides the methods for reading and casting an incoming entity to the object
 *
 * @see AbstractReaderWriter
 * @author pbouda
 */
public interface Reader extends Transformer {

	/**
	 * Reads the entity from an incoming request and creates the object from that
	 *
	 * @param request an incoming request
	 * @param clazz class to which is an entity cast
	 * @param <T> type of the result object
	 * @return created object
	 */
	<T> T readFrom(InternalRequest<T> request, Type<T> clazz);

	/**
	 * Figures out whether is reader compatible for the given route
	 *
	 * @param route compared route
	 * @return returns {@code true} if the reader is compatible with the given route
	 */
	boolean isReaderCompatible(InternalRoute route);

}
