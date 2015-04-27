package org.joyrest.jetty.handler;

	import static java.util.Collections.list;
	import static org.joyrest.utils.RequestResponseUtils.createPath;

	import java.io.IOException;

	import javax.servlet.ServletException;
	import javax.servlet.http.*;

	import org.eclipse.jetty.server.Request;
	import org.eclipse.jetty.server.handler.AbstractHandler;
	import org.joyrest.context.ApplicationContext;
	import org.joyrest.model.http.HttpMethod;
	import org.joyrest.model.request.LambdaRequest;
	import org.joyrest.model.response.LambdaResponse;
	import org.joyrest.processor.*;

	public class JettyApplicationHandler extends AbstractHandler {

			/* Class for processing an incoming request and generated response */
			private final RequestProcessor processor;

			public JettyApplicationHandler(ApplicationContext applicationContext) {
				this(new RequestProcessorImpl(applicationContext));
			}

			public JettyApplicationHandler(RequestProcessor processor) {
				this.processor = processor;
			}

			@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
				try {
						processor.process(createRequest(request), createResponse(response));
			            baseRequest.setHandled(true);
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