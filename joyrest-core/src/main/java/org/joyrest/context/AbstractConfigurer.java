package org.joyrest.context;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

import java.util.*;

import org.joyrest.aspect.Aspect;
import org.joyrest.common.annotation.General;
import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.exception.configuration.TypedExceptionConfiguration;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.exception.type.RestException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.InternalRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.StringReaderWriter;
import org.joyrest.transform.Transformer;
import org.joyrest.transform.Writer;
import org.joyrest.transform.aspect.SerializationAspect;

@SuppressWarnings("rawtypes")
public abstract class AbstractConfigurer<T> implements Configurer<T> {

	/* ServiceLocator name in its own context */
	private static final JoyLogger log = new JoyLogger(AbstractConfigurer.class);

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

		Collection<Aspect> aspects = getAspects();
		aspects.addAll(REQUIRED_ASPECTS);
		duplicationCheck(aspects);
		Collection<Aspect> sortedAspects = getSortedAspects(aspects);

		Collection<ExceptionConfiguration> exceptionConfigurations = getExceptionConfigurations();
		exceptionConfigurations.add(new InternalExceptionConfiguration());

		Map<Class<? extends Exception>, InternalExceptionHandler> handlers = exceptionConfigurations.stream()
			.peek(ExceptionConfiguration::initialize)
			.flatMap(config -> config.getExceptionHandlers().stream())
			.peek(handler -> {
				populateHandlerWriters(writers, handler, nonNull(handler.getResponseType()));
				logExceptionHandler(handler);
			})
			.collect(toMap(InternalExceptionHandler::getExceptionClass, identity()));

		Set<InternalRoute> routes = getControllerConfiguration().stream()
			.peek(ControllerConfiguration::initialize)
			.flatMap(config -> config.getRoutes().stream())
			.peek(route -> {
				route.aspect(sortedAspects.toArray(new Aspect[sortedAspects.size()]));
				populateRouteReaders(readers, route, nonNull(route.getRequestType()));
				populateRouteWriters(writers, route, nonNull(route.getResponseType()));
				logRoute(route);
			}).collect(toSet());

		ApplicationContextImpl context = new ApplicationContextImpl();
		context.setRoutes(routes);
		context.setExceptionHandlers(handlers);
		return context;
	}

	private Collection<Aspect> getSortedAspects(Collection<Aspect> aspects) {
		Comparator<Aspect> aspectComparator =
			(e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder());

		return aspects.stream()
			.sorted(aspectComparator)
			.collect(toList());
	}

	private void duplicationCheck(Collection<Aspect> aspects) {
		Set<Aspect> duplicated = aspects.stream()
			.filter(n -> aspects.stream()
					.filter(x -> x == n).count() > 1)
			.collect(toSet());

		if (!duplicated.isEmpty()) {
			String duplicatedOrders = duplicated.stream()
				.map(i -> String.valueOf(i.getOrder()))
				.collect(joining(", "));

			throw new InvalidConfigurationException(
				"There is registered more than one aspect with the given order: " + duplicatedOrders);
		}
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

	private static void populateHandlerWriters(Map<Boolean, List<Writer>> writers, InternalExceptionHandler handler,
			boolean condition) {
		if (condition) {
			writers.get(Boolean.TRUE).stream().distinct()
				.forEach(handler::addWriter);

			writers.get(Boolean.FALSE).stream().distinct()
				.filter(writer -> writer.isClassCompatible(handler.getResponseType().getType()))
				.forEach(handler::addWriter);
		}
	}

	private static void logExceptionHandler(InternalExceptionHandler handler) {
		log.info(() -> String.format("Exception Handler instantiated: EXCEPTION-CLASS[%s], RESP-CLASS[%s]",
				handler.getExceptionClass(), handler.getResponseType()));

		handler.getWriters().forEach((type, writer) ->
				log.debug(() -> String.format("Writer [%s, %s] added to the Exception Handler [EXCEPTION-CLASS[%s]]",
						writer.getClass().getSimpleName(), writer.getMediaType(), handler.getExceptionClass())));
	}

	private static void logRoute(InternalRoute route) {
		log.info(() -> String.format(
				"Route instantiated: METHOD[%s], PATH[%s], CONSUMES[%s], PRODUCES[%s], REQ-CLASS[%s], RESP-CLASS[%s]",
				route.getHttpMethod(), route.getPath(), route.getConsumes(), route.getProduces(),
				route.getRequestType(), route.getResponseType()));

		route.getAspects().stream()
			.forEach(aspect ->
					log.debug(() -> String.format("Aspect [%s] added to the Route [METHOD[%s], PATH[%s]]",
							aspect.getClass().getSimpleName(), route.getHttpMethod(), route.getPath())));

		route.getReaders().forEach(
				(type, reader) ->
				log.debug(() -> String.format("Reader [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
						reader.getClass().getSimpleName(), reader.getMediaType(), route.getHttpMethod(), route.getPath())));

		route.getWriters().forEach(
				(type, writer) ->
				log.debug(() -> String.format("Writer [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
						writer.getClass().getSimpleName(), writer.getMediaType(), route.getHttpMethod(), route.getPath())));
	}

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
