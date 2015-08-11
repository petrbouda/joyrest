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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.context.configurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.ApplicationContextImpl;
import org.joyrest.context.autoconfigurar.AutoConfigurer;
import org.joyrest.context.initializer.BeanFactory;
import org.joyrest.context.initializer.InitContext;
import org.joyrest.exception.InternalExceptionConfiguration;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.exception.interceptor.ExceptionHandlerInterceptor;
import org.joyrest.exception.type.RestException;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.interceptor.PathParamProcessingInterceptor;
import org.joyrest.transform.AbstractReaderWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.joyrest.transform.interceptor.SerializationInterceptor;
import static org.joyrest.context.helper.CheckHelper.orderDuplicationCheck;
import static org.joyrest.context.helper.ConfigurationHelper.createTransformers;
import static org.joyrest.context.helper.ConfigurationHelper.sort;
import static org.joyrest.context.helper.LoggingHelper.logExceptionHandler;
import static org.joyrest.context.helper.LoggingHelper.logRoute;
import static org.joyrest.context.helper.PopulateHelper.populateHandlerWriters;
import static org.joyrest.context.helper.PopulateHelper.populateRouteReaders;
import static org.joyrest.context.helper.PopulateHelper.populateRouteWriters;
import static org.joyrest.utils.CollectionUtils.concat;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * Abstract class as a helper for initialization an {@link ApplicationContext}.
 *
 * @param <T> type of configurer class which is used to set up a configurer
 * @author pbouda
 * @see Configurer
 * @see NonDiConfigurer
 */
public abstract class AbstractConfigurer<T> implements Configurer<T> {

    protected static final List<Interceptor> COMMON_INTERCEPTORS;

    protected static final List<ExceptionConfiguration> COMMON_HANDLERS = singletonList(
        new InternalExceptionConfiguration());

    static {
        COMMON_INTERCEPTORS = new ArrayList<>();
        COMMON_INTERCEPTORS.add(new SerializationInterceptor());
        COMMON_INTERCEPTORS.add(new PathParamProcessingInterceptor());
    }

    /**
     * Method causes the initialization of the application context using the methods which returns a collection of beans such as
     *
     * @return initialized {@code application context}
     */
    protected ApplicationContext initializeContext() {
        Function<Class<Object>, List<Object>> getBeans = this::getBeans;

        BeanFactory beanFactory = new BeanFactory(getBeans);
        InitContext context = new InitContext();

        AutoConfigurer.configureInitializers()
            .forEach(initializer -> initializer.init(context, beanFactory));

        List<AbstractReaderWriter> readersWriters = AutoConfigurer.configureReadersWriters();

        ExceptionHandlerInterceptor exceptionHandlerInterceptor = new ExceptionHandlerInterceptor();
        COMMON_INTERCEPTORS.add(exceptionHandlerInterceptor);
        Map<Boolean, List<Reader>> readers = createTransformers(
            concat(beanFactory.getAll(Reader.class), context.getReaders(), readersWriters));
        Map<Boolean, List<Writer>> writers = createTransformers(
            concat(beanFactory.getAll(Writer.class), context.getWriters(), readersWriters));
        List<Interceptor> interceptors = sort(
            concat(beanFactory.getAll(Interceptor.class), context.getInterceptors(), COMMON_INTERCEPTORS));
        List<ExceptionConfiguration> handlers =
            concat(beanFactory.getAll(ExceptionConfiguration.class), context.getExceptionConfigurations(), COMMON_HANDLERS);
        List<ControllerConfiguration> controllers =
            concat(beanFactory.getAll(ControllerConfiguration.class), context.getControllerConfigurations());

        orderDuplicationCheck(interceptors);

        Map<Class<? extends Exception>, InternalExceptionHandler> handlerMap =
            handlers.stream()
                .peek(ExceptionConfiguration::initialize)
                .flatMap(config -> config.getExceptionHandlers().stream())
                .peek(handler -> {
                    populateHandlerWriters(writers, handler, nonNull(handler.getResponseType()));
                    logExceptionHandler(handler);
                })
                .collect(toMap(InternalExceptionHandler::getExceptionClass, identity()));
        context.setExceptionHandlers(handlerMap);

        controllers.stream()
            .peek(ControllerConfiguration::initialize)
            .flatMap(config -> config.getRoutes().stream())
            .peek(route -> {
                route.interceptor(interceptors.toArray(new Interceptor[interceptors.size()]));
                populateRouteReaders(readers, route);
                populateRouteWriters(writers, route);
                logRoute(route);
            }).forEach(context::addRoute);

        ApplicationContextImpl applicationContext = new ApplicationContextImpl();
        applicationContext.setRoutes(context.getRoutes());
        applicationContext.setExceptionHandlers(context.getExceptionHandlers());

        exceptionHandlerInterceptor.setApplicationContext(applicationContext);
        return applicationContext;
    }

}
