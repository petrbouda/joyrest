package org.joyrest.servlet;

import static java.util.Collections.list;
import static org.joyrest.servlet.ServletProperties.APPLICATION_JAVA_CONFIG_PROPERTY;
import static org.joyrest.servlet.ServletProperties.CONFIGURER_PROPERTY;
import static org.joyrest.utils.RequestResponseUtils.createPath;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.Configurer;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.LambdaRequest;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

public class ServletApplicationHandler extends HttpServlet implements Filter {

	private static final long serialVersionUID = -4298969347996942699L;

	private final static JoyLogger log = new JoyLogger(ServletApplicationHandler.class);

	/* Class for processing an incoming request and generated response */
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

	private void initializeProcessor(Function<String, String> paramProvider) throws ServletException {
		if (this.applicationConfig == null) {
			this.applicationConfig = getInstanceFromClazz(paramProvider.apply(APPLICATION_JAVA_CONFIG_PROPERTY));
		}

		if (this.configurer == null) {
			this.configurer = getInstanceFromClazz(paramProvider.apply(CONFIGURER_PROPERTY), Configurer.class);
		}

		@SuppressWarnings("unchecked")
		ApplicationContext context = configurer.initialize(applicationConfig);
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
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if(req.getDispatcherType() == DispatcherType.ERROR)
				return;

			processor.process(createRequest(req), createResponse(resp));
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private LambdaResponse<Object> createResponse(HttpServletResponse response) throws IOException {
		LambdaResponse<Object> joyResponse = new LambdaResponse<>(response::addHeader,
				status -> response.setStatus(status.code()));
		joyResponse.setOutputStream(response.getOutputStream());
		return joyResponse;
	}

	private LambdaRequest<Object> createRequest(HttpServletRequest request) throws IOException {
		LambdaRequest<Object> joyRequest = new LambdaRequest<>(request::getHeader, request::getParameterValues);
		joyRequest.setPath(createPath(request.getRequestURI(), request.getContextPath()));
		joyRequest.setMethod(HttpMethod.of(request.getMethod()));
		joyRequest.setInputStream(request.getInputStream());
		joyRequest.setQueryParamNames(list(request.getParameterNames()));
		joyRequest.setHeaderNames(list(request.getHeaderNames()));
		return joyRequest;
	}
}