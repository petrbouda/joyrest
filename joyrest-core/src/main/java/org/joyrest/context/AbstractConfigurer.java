package org.joyrest.context;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joyrest.aspect.Aspect;
import org.joyrest.common.annotation.General;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.exception.type.RestException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.StringReaderWriter;
import org.joyrest.transform.Transformer;
import org.joyrest.transform.Writer;
import org.joyrest.transform.aspect.SerializationAspect;

@SuppressWarnings("rawtypes")
public abstract class AbstractConfigurer<T> implements Configurer<T> {

	private final static JoyLogger log = new JoyLogger(AbstractConfigurer.class);

	/* ServiceLocator name in its own context */
	public static final String JOYREST_BEAN_CONTEXT = "JoyRestBeanContext";

	private final StringReaderWriter stringReaderWriter = new StringReaderWriter();

	protected final List<Aspect> REQUIRED_ASPECTS = singletonList(new SerializationAspect());
	protected final List<Reader> REQUIRED_READERS = singletonList(stringReaderWriter);
	protected final List<Writer> REQUIRED_WRITERS = singletonList(stringReaderWriter);

	protected abstract Collection<Aspect> getAspects();

	protected abstract Collection<Reader> getReaders();

	protected abstract Collection<Writer> getWriters();

	protected abstract Collection<ExceptionConfiguration> getExceptionConfigurations();

	protected abstract Collection<ControllerConfiguration> getControllerConfiguration();

	protected ApplicationContext initializeContext() {
		Map<Boolean, List<Reader>> readers = createTransformers(getReaders(), REQUIRED_READERS);
		Map<Boolean, List<Writer>> writers = createTransformers(getWriters(), REQUIRED_WRITERS);

		Collection<ExceptionConfiguration> exceptionConfigurations = getExceptionConfigurations();
		exceptionConfigurations.add(new InternalExceptionConfiguration());

		Map<Class<? extends Exception>, InternalExceptionHandler> handlers = exceptionConfigurations.stream()
			.peek(ExceptionConfiguration::initialize)
			.flatMap(config -> config.getExceptionHandlers().stream())
			.peek(handler -> {
				logExceptionHandler(handler);

				populateHandlerWriters(writers, handler, nonNull(handler.getResponseType()));
				logHandlerWriters(handler);
			})
			.collect(toMap(InternalExceptionHandler::getExceptionClass, identity()));

		Collection<Aspect> aspects = getAspects();
		aspects.addAll(REQUIRED_ASPECTS);

		// List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 4, 4);
		// Set<Integer> duplicated = numbers.stream()
		// .filter(n -> numbers.stream().filter(x -> x == n).count() > 1).collect(Collectors.toSet());

		Set<InternalRoute> routes = getControllerConfiguration().stream()
			.peek(ControllerConfiguration::initialize)
			.flatMap(config -> config.getRoutes().stream())
			.peek(route -> {
				logRoute(route);

				route.aspect(aspects.toArray(new Aspect[aspects.size()]));
				logAspects(route);

				populateRouteReaders(readers, route, nonNull(route.getRequestType()));
				logRouteReaders(route);

				populateRouteWriters(writers, route, nonNull(route.getResponseType()));
				logRouteWriters(route);
			}).collect(toSet());

		ApplicationContextImpl context = new ApplicationContextImpl();
		context.setRoutes(routes);
		context.setExceptionHandlers(handlers);
		return context;
	}

	private static <T extends Transformer> Map<Boolean, List<T>> createTransformers(Collection<T> transform, List<T> additional) {
		transform.addAll(additional);
		return transform.stream()
			.collect(partitioningBy(General::isGeneral));
	}

	private static void populateRouteReaders(Map<Boolean, List<Reader>> readers, InternalRoute route, boolean condition) {
		if (condition) {
			readers.get(Boolean.TRUE).stream().distinct()
				.filter(transformer -> transformer.isReaderCompatible(route))
				.forEach(route::addReader);

			readers.get(Boolean.FALSE).stream().distinct()
				.filter(reader -> reader.isReaderCompatible(route))
				.forEach(route::addReader);
		}
	}

	private static void populateRouteWriters(Map<Boolean, List<Writer>> writers, InternalRoute route, boolean condition) {
		if (condition) {
			writers.get(Boolean.TRUE).stream().distinct()
				.filter(writer -> writer.isWriterCompatible(route))
				.forEach(route::addWriter);

			writers.get(Boolean.FALSE).stream().distinct()
				.filter(writer -> writer.isWriterCompatible(route))
				.forEach(route::addWriter);
		}
	}

	private static void populateHandlerWriters(Map<Boolean, List<Writer>> writers, InternalExceptionHandler handler, boolean condition) {
		if (condition) {
			writers.get(Boolean.TRUE).stream().distinct()
				.forEach(handler::addWriter);

			writers.get(Boolean.FALSE).stream().distinct()
				.filter(writer -> writer.getWriterCompatibleClass().isPresent())
				.filter(writer -> writer.getWriterCompatibleClass().get() == handler.getExceptionClass())
				.forEach(handler::addWriter);
		}
	}

	private static void logRoute(InternalRoute route) {
		log.info(() -> String.format(
				"Route instantiated: METHOD[%s], PATH[%s], CONSUMES[%s], PRODUCES[%s], REQ-CLASS[%s], RESP-CLASS[%s]",
				route.getHttpMethod(), route.getPath(), route.getConsumes(), route.getProduces(),
				route.getRequestType(), route.getResponseType()));
	}

	private static void logExceptionHandler(InternalExceptionHandler handler) {
		log.info(() -> String.format("Exception Handler instantiated: EXCEPTION-CLASS[%s], RESP-CLASS[%s]",
				handler.getExceptionClass(), handler.getResponseType()));
	}

	private static void logRouteReaders(InternalRoute route) {
		route.getReaders().forEach((type, reader) ->
				log.debug(() -> String.format("Reader [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
						reader.getClass().getSimpleName(), reader.getMediaType(), route.getHttpMethod(), route.getPath())));
	}

	private static void logRouteWriters(InternalRoute route) {
		route.getWriters().forEach((type, writer) ->
				log.debug(() -> String.format("Writer [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
						writer.getClass().getSimpleName(), writer.getMediaType(), route.getHttpMethod(), route.getPath())));
	}

	private static void logHandlerWriters(InternalExceptionHandler handler) {
		handler.getWriters().forEach((type, writer) ->
				log.debug(() -> String.format("Writer [%s, %s] added to the Exception Handler [EXCEPTION-CLASS[%s]]",
						writer.getClass().getSimpleName(), writer.getMediaType(), handler.getExceptionClass())));
	}

	private static void logAspects(InternalRoute route) {
		route.getAspects().forEach(aspect ->
				log.debug(() -> String.format("Aspect [%s] added to the Route [METHOD[%s], PATH[%s]]",
						aspect.getClass().getSimpleName(), route.getHttpMethod(), route.getPath())));
	}

	private class InternalExceptionConfiguration extends TypedExceptionConfiguration {

		@Override
		protected void configure() {
			handle(RestException.class, (req, resp, ex) -> {
				InternalResponse<?> exResp = ex.getResponse();
				resp.status(exResp.getStatus());
				exResp.getHeaders().forEach(resp::header);
			});
		}
	}

}
