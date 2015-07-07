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
package org.joyrest.context.initializer;

import java.util.List;
import java.util.Map;

import org.joyrest.context.autoconfigurar.AutoConfigurer;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.exception.type.RestException;
import org.joyrest.interceptor.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
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

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class MainInitializer implements Initializer {

    protected final List<Interceptor> PREDEFINED_INTERCEPTORS = singletonList(new SerializationInterceptor());

    protected final List<ExceptionConfiguration> PREDEFINED_HANDLERS = singletonList(new InternalExceptionConfiguration());

    @Override
    public void init(InitContext context, BeanFactory beanFactory) {
        List<AbstractReaderWriter> readersWriters = AutoConfigurer.configureReadersWriters();

        Map<Boolean, List<Reader>> readers = createTransformers(
            concat(beanFactory.getAll(Reader.class), context.getReaders(), readersWriters));
        Map<Boolean, List<Writer>> writers = createTransformers(
            concat(beanFactory.getAll(Writer.class), context.getWriters(), readersWriters));
        List<Interceptor> interceptors = sort(
            concat(beanFactory.getAll(Interceptor.class), context.getInterceptors(), PREDEFINED_INTERCEPTORS));
        List<ExceptionConfiguration> handlers =
            concat(beanFactory.getAll(ExceptionConfiguration.class), context.getExceptionConfigurations(),
                PREDEFINED_HANDLERS);
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
    }

    /**
     * Internal implementation of {@link RestException} handler.
     */
    private class InternalExceptionConfiguration extends TypedExceptionConfiguration {

        @Override
        protected void configure() {
            handle(RestException.class, (req, resp, ex) -> {
                resp.status(ex.getStatus());
                ex.getHeaders()
                    .forEach(resp::header);
            });
        }
    }

}
