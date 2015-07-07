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
package org.joyrest.oauth2.context;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;

public class AuthorizationContext extends ApplicationContextAdapter {

    private final ConcurrentHashMap<Class<?>, Object> beanFactory = new ConcurrentHashMap<>();

    private AuthorizationContext() {
    }

    public static AuthorizationContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void putBean(Class<?> clazz, Object object) {
        beanFactory.put(clazz, object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) throws BeansException {
        return (T) beanFactory.get(type);
    }

    private static class SingletonHolder {

        private static final AuthorizationContext INSTANCE = new AuthorizationContext();
    }

}
