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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

public class ApplicationContextAdapter implements ApplicationContext {

    @Override
    public String getId() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String getApplicationName() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String getDisplayName() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public long getStartupDate() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public ApplicationContext getParent() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean containsLocalBean(String name) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public int getBeanDefinitionCount() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String[] getBeanDefinitionNames() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
        throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
        throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Object getBean(String name) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean containsBean(String name) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String[] getAliases(String name) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Environment getEnvironment() {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public Resource getResource(String location) {
        throw new UnsupportedOperationException("This operation is not used.");
    }

    @Override
    public ClassLoader getClassLoader() {
        throw new UnsupportedOperationException("This operation is not used.");
    }
}
