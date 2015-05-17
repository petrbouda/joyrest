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

import org.joyrest.common.annotation.General;
import org.joyrest.model.http.MediaType;

/**
 * Implementation must configure media-type and class compatibility and whether is a transformer general for given
 * media-type or not.
 *
 * @see Reader
 * @see Writer
 * @author pbouda
 */
public interface Transformer extends General {

	/**
	 * Returns compatible media-type with this transformer
	 *
	 * @return media-type
	 */
	MediaType getMediaType();

	/**
	 * Compares whether the class is suitable for this transformer
	 *
	 * @param clazz class to compare
	 * @return returns {@code true} if the class is compatible with the given transformer
	 */
	boolean isClassCompatible(Class<?> clazz);

}
