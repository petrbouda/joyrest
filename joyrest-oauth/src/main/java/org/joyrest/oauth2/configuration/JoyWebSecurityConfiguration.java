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
package org.joyrest.oauth2.configuration;

import static java.util.Objects.nonNull;
import static org.joyrest.utils.ExceptionUtils.rethrowConsumer;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;

import org.joyrest.logging.JoyLogger;
import org.joyrest.oauth2.InitializedBeanPostProcessor;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

public class JoyWebSecurityConfiguration {

	private static final JoyLogger log = new JoyLogger(JoyWebSecurityConfiguration.class);

	private WebSecurity webSecurity;
	private Filter securityFilterChain;
	private DelegatingApplicationListener delegatingApplicationListener;

	public JoyWebSecurityConfiguration(List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers) {
		if (isEmpty(webSecurityConfigurers))
			throw new IllegalStateException("At least one non-null instance of " + WebSecurityConfigurer.class.getSimpleName() +
				" must be passed to. " + JoyWebSecurityConfiguration.class.getSimpleName() + ". Hint try extending " +
				WebSecurityConfigurerAdapter.class.getSimpleName());

		setFilterChainProxySecurityConfigurer(new InitializedBeanPostProcessor(), webSecurityConfigurers);
	}

	public Filter springSecurityFilterChain() {
		if (nonNull(securityFilterChain))
			return securityFilterChain;

		try {
			securityFilterChain = webSecurity.build();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot configure 'springSecurityFilterChain'.", e);
		}

		return securityFilterChain;
	}

	public SecurityExpressionHandler<FilterInvocation> getSecurityExpressionHandler() {
		if (nonNull(securityFilterChain))
			return webSecurity.getExpressionHandler();

		springSecurityFilterChain();
		return webSecurity.getExpressionHandler();
	}

	public WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() throws Exception {
		if (nonNull(securityFilterChain))
			return webSecurity.getPrivilegeEvaluator();

		springSecurityFilterChain();
		return webSecurity.getPrivilegeEvaluator();
	}

	public DelegatingApplicationListener getDelegatingApplicationListener() {
		if (nonNull(delegatingApplicationListener))
			return delegatingApplicationListener;

		delegatingApplicationListener = new DelegatingApplicationListener();
		return delegatingApplicationListener;
	}

	private void setFilterChainProxySecurityConfigurer(ObjectPostProcessor<Object> objectPostProcessor,
			List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers) {

		webSecurity = new WebSecurity(objectPostProcessor);
		webSecurity.debug(log.isDebug());
		Collections.sort(webSecurityConfigurers, AnnotationAwareOrderComparator.INSTANCE);
		checkOrderDuplication(webSecurityConfigurers);

		webSecurityConfigurers.stream()
			.forEach(rethrowConsumer(webSecurity::apply));
	}

	private void checkOrderDuplication(List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers) {
		Integer previousOrder = null;
		for (SecurityConfigurer<Filter, WebSecurity> config : webSecurityConfigurers) {
			Integer order = AnnotationAwareOrderComparator.lookupOrder(config);
			if (nonNull(previousOrder) && previousOrder.equals(order))
				throw new IllegalStateException("@Order on WebSecurityConfigurers must be unique. Order of " + order +
						" was already used, so it cannot be used on " + config + " too.");

			previousOrder = order;
		}
	}

	/**
	 * A custom verision of the Spring provided AnnotationAwareOrderComparator that uses
	 * {@link AnnotationUtils#findAnnotation(Class, Class)} to look on super class instances for the {@link Order} annotation.
	 *
	 * @author Rob Winch
	 * @since 3.2
	 */
	private static class AnnotationAwareOrderComparator extends OrderComparator {

		private static final AnnotationAwareOrderComparator INSTANCE = new AnnotationAwareOrderComparator();

		private static int lookupOrder(Object obj) {
			if (obj instanceof Ordered) {
				return ((Ordered) obj).getOrder();
			}
			if (obj != null) {
				Class<?> clazz = (obj instanceof Class ? (Class<?>) obj : obj.getClass());
				Order order = AnnotationUtils.findAnnotation(clazz, Order.class);
				if (order != null) {
					return order.value();
				}
			}
			return Ordered.LOWEST_PRECEDENCE;
		}

		@Override
		protected int getOrder(Object obj) {
			return lookupOrder(obj);
		}
	}
}