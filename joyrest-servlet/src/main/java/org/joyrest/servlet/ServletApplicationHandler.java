package org.joyrest.servlet;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.Configurer;
import org.joyrest.model.http.HttpMethod;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.LambdaResponse;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.processor.RequestProcessor;
import org.joyrest.processor.RequestProcessorImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

import static java.util.Collections.list;
import static org.joyrest.servlet.ServletProperties.APPLICATION_JAVA_CONFIG_PROPERTY;
import static org.joyrest.servlet.ServletProperties.CONFIGURER_PROPERTY;
import static org.joyrest.utils.RequestResponseUtils.*;

public class ServletApplicationHandler extends HttpServlet implements Filter {

	/* Class for processing an incoming request and generated response */
	private RequestProcessor processor;

	private Configurer configurer;

	private Object applicationConfig;

	public ServletApplicationHandler() {
	}

	public ServletApplicationHandler(Configurer configurer, Object applicationConfig) {
		this.configurer = configurer;
		this.applicationConfig = applicationConfig;
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

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			InternalRequest request = createJoyRequest(req);
			InternalResponse response = createJoyResponse(resp);

			/*
			 * Processes the given client's request and using ConsumerResponse automatically populate
			 * HttpServletResponse. There is no need of an additional response population.
			 */
			processor.process(request, response);
		} catch (Exception e) {
			throw new ServletException("Error occurs during processing a request.", e);
		}
	}

	private InternalResponse createJoyResponse(HttpServletResponse response) throws IOException {
		LambdaResponse joyResponse = new LambdaResponse(response::addHeader,
			status -> response.setStatus(status.code()));
		joyResponse.setOutputStream(response.getOutputStream());
		return joyResponse;
	}

	private InternalRequest createJoyRequest(HttpServletRequest request) throws IOException {
		InternalRequest joyRequest = new InternalRequest();
		joyRequest.setPath(createPath(request.getRequestURI(), request.getContextPath()));
		joyRequest.setMethod(HttpMethod.of(request.getMethod()));
		joyRequest.setRequestBody(request.getInputStream());
		joyRequest.setQueryParams(createQueryParams(list(request.getParameterNames()), request::getParameterValues));
		joyRequest.setHeaders(createHeaders(list(request.getHeaderNames()), request::getHeader));
		return joyRequest;
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
}