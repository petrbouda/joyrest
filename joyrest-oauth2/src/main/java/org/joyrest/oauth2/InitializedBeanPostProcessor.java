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
package org.joyrest.oauth2;

import org.joyrest.logging.JoyLogger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.config.annotation.ObjectPostProcessor;

public final class InitializedBeanPostProcessor implements ObjectPostProcessor<Object>, DisposableBean {

    private static final JoyLogger log = new JoyLogger(InitializedBeanPostProcessor.class);

    public <T> T postProcess(T object) {
        if (object instanceof InitializingBean) {
            InitializingBean initializingBean = (InitializingBean) object;
            try {
                initializingBean.afterPropertiesSet();
            } catch (Exception e) {
                throw new IllegalStateException("An error occurred during initializing bean: " + object, e);
            }
        }

        return object;
    }

    @Override
    public void destroy() throws Exception {

    }
}
