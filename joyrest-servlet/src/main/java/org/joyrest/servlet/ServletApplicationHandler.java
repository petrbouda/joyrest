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
package org.joyrest.servlet;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.Configurer;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;
import org.joyrest.servlet.model.ServletRequestWrapper;
import org.joyrest.servlet.model.ServletResponseWrapper;
import static org.joyrest.servlet.JoyrestProperties.APPLICATION_JAVA_CONFIG_PROPERTY;
import static org.joyrest.servlet.JoyrestProperties.CONFIGURER_PROPERTY;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ServletApplicationHandler extends HttpServlet implements Filter {

    private static final long serialVersionUID = -4298969347996942699L;

    private final static JoyLogger log = new JoyLogger(ServletApplicationHandler.class);

    /* Class for processing an incoming model and generated response */
    private RequestProcessor processor;

    private Configurer configurer;

    private Object applicationConfig;

    public ServletApplicationHandler() {
    }

    public ServletApplicationHandler(Configurer<?> configurer, Object applicationConfig) {
        this.configurer = configurer;
        this.applicationConfig = applicationConfig;
    }

    private static Object getInstanceFromClazz(String clazzName) throws ServletException {
        return getInstanceFromClazz(clazzName, Object.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInstanceFromClazz(String clazzName, Class<T> expectedClazz) throws ServletException {
        try {
            Class<?> clazz = Class.forName(clazzName);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new ServletException("Invalid expected class", e);
        }
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        initializeProcessor(servletConfig::getInitParameter);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        initializeProcessor(filterConfig::getInitParameter);
    }

    @SuppressWarnings("unchecked")
    private void initializeProcessor(Function<String, String> paramProvider) throws ServletException {
        if (isNull(applicationConfig)) {
            String configClass = paramProvider.apply(APPLICATION_JAVA_CONFIG_PROPERTY);
            if (nonNull(configClass)) {
                this.applicationConfig = getInstanceFromClazz(configClass);
            } else {
                throw new InvalidConfigurationException("Servlet Handler cannot be initialized because property '"
                    + APPLICATION_JAVA_CONFIG_PROPERTY + "' missing.");
            }
        }

        if (isNull(configurer)) {
            String configurerClass = paramProvider.apply(CONFIGURER_PROPERTY);
            if (nonNull(configurerClass)) {
                this.configurer = getInstanceFromClazz(paramProvider.apply(CONFIGURER_PROPERTY), Configurer.class);
            } else {
                throw new InvalidConfigurationException("Servlet Handler cannot be initialized because property '"
                    + CONFIGURER_PROPERTY + "' missing.");
            }
        }

        ApplicationContext context;
        if (nonNull(applicationConfig)) {
            context = configurer.initialize(applicationConfig);
        } else {
            throw new InvalidConfigurationException("Servlet Handler cannot be initialized because of " +
                "configurer without an application config.");
        }
        this.processor = new RequestProcessorImpl(context);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        try {
            processRequest((HttpServletRequest) req, (HttpServletResponse) resp);
        } catch (ClassCastException cce) {
            throw new ServletException("This is not Http Servlet Filter", cce);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(request, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getDispatcherType() == DispatcherType.ERROR) {
                return;
            }

            processor.process(new ServletRequestWrapper(req), new ServletResponseWrapper(resp));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}