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
package org.joyrest.routing.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ResponseCollectionType<T> extends CollectionType<T> {

	public ResponseCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> ResponseCollectionType<List<P>> RespList(Class<P> param) {
		return new ResponseCollectionType<>(List.class, param);
	}

	public static <P> ResponseCollectionType<Set<P>> RespSet(Class<P> param) {
		return new ResponseCollectionType<>(Set.class, param);
	}

	public static <P> ResponseCollectionType<Collection<P>> RespCol(Class<P> param) {
		return new ResponseCollectionType<>(Collection.class, param);
	}

}
