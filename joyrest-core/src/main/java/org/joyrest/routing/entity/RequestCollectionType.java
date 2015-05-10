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

public class RequestCollectionType<T> extends CollectionType<T> {

	public RequestCollectionType(Class<?> type, Class<?> param) {
		super(type, param);
	}

	public static <P> RequestCollectionType<List<P>> ReqList(Class<P> param) {
		return new RequestCollectionType<>(List.class, param);
	}

	public static <P> RequestCollectionType<Set<P>> ReqSet(Class<P> param) {
		return new RequestCollectionType<>(Set.class, param);
	}

	public static <P> RequestCollectionType<Collection<P>> ReqCol(Class<P> param) {
		return new RequestCollectionType<>(Collection.class, param);
	}
}
