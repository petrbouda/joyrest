package org.joyrest.servlet;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.joyrest.servlet.JoyrestProperties.APPLICATION_JAVA_CONFIG_PROPERTY;
import static org.joyrest.servlet.JoyrestProperties.CONFIGURER_PROPERTY;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.Configurer;
import org.joyrest.context.NonDiConfigurer;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;
import org.joyrest.servlet.model.ServletRequestWrapper;
import org.joyrest.servlet.model.ServletResponseWrapper;

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
			if(nonNull(configClass))
				this.applicationConfig = getInstanceFromClazz(configClass);
		}

		if (isNull(configurer)) {
			String configurerClass = paramProvider.apply(CONFIGURER_PROPERTY);
			if(nonNull(configurerClass))
				this.configurer = getInstanceFromClazz(paramProvider.apply(CONFIGURER_PROPERTY), Configurer.class);
			else
				throw new InvalidConfigurationException("Servlet Handler cannot be initialized because property '"
					+ CONFIGURER_PROPERTY + "' missing.");
		}

		ApplicationContext context;
		if(nonNull(applicationConfig)) {
			context = configurer.initialize(applicationConfig);
		} else {
			if(configurer instanceof NonDiConfigurer)
				context = ((NonDiConfigurer) configurer).initialize();
			else
				throw new InvalidConfigurationException("Servlet Handler cannot be initialized because of DI-dependent " +
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
			if (req.getDispatcherType() == DispatcherType.ERROR)
				return;

			processor.process(new ServletRequestWrapper(req), new ServletResponseWrapper(resp));
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}