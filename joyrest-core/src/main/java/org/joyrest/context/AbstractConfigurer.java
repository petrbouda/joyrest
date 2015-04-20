package org.joyrest.context;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.joyrest.aspect.Aspect;
import org.joyrest.common.annotation.General;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.exception.handler.RestExceptionHandler;
import org.joyrest.exception.type.RestException;
import org.joyrest.function.TriConsumer;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.http.MediaType;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.EntityRoute;
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

	protected ApplicationContext initializeContext() {
		Map<Boolean, List<Reader>> readers = createTransformers(getBeans(Reader.class), REQUIRED_READERS);
		Map<Boolean, List<Writer>> writers = createTransformers(getBeans(Writer.class), REQUIRED_WRITERS);

		Map<Class<? extends Exception>, TriConsumer<InternalRequest<?>, InternalResponse<?>, ? extends Exception>> handlers =
				getBeans(ExceptionConfiguration.class).stream()
					.peek(ExceptionConfiguration::initialize)
					.flatMap(config -> config.getExceptionHandlers().entrySet().stream())
					.collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

		// Default Handler for RestException
		handlers.put(RestException.class, new RestExceptionHandler());

		List<Aspect> aspects = getBeans(Aspect.class);
		aspects.addAll(REQUIRED_ASPECTS);

		// List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 4, 4);
		// Set<Integer> duplicated = numbers.stream()
		// .filter(n -> numbers.stream().filter(x -> x == n).count() > 1).collect(Collectors.toSet());

		Set<EntityRoute> routes = getBeans(ControllerConfiguration.class).stream()
			.peek(ControllerConfiguration::initialize)
			.flatMap(config -> config.getRoutes().stream())
			.peek(route -> {
				logRoute(route);
				setAspects(route, aspects.toArray(new Aspect[aspects.size()]));
				populateReader(readers, route, nonNull(route.getRequestType()));
				populateWriter(writers, route, nonNull(route.getResponseType()));
			}).collect(Collectors.toSet());

		ApplicationContextImpl context = new ApplicationContextImpl();
		context.setRoutes(routes);
		context.setExceptionHandlers(handlers);
		context.setExceptionWriters(createExceptionWriters(writers));
		return context;
	}

	private static <T extends Transformer> Map<Boolean, List<T>> createTransformers(List<T> writers, List<T> additional) {
		writers.addAll(additional);
		return writers.stream()
			.collect(partitioningBy(General::isGeneral));
	}

	private static Map<MediaType, Writer> createExceptionWriters(Map<Boolean, List<Writer>> writers) {
		return writers.values().stream()
			.flatMap(List::stream)
			.distinct()
			.collect(toMap(Transformer::getMediaType, identity()));
	}

	private static <X extends Reader> void populateReader(Map<Boolean, List<X>> readers, EntityRoute route, boolean condition) {
		if (condition) {
			readers.get(Boolean.FALSE).stream().distinct()
				.filter(reader -> reader.isReaderCompatible(route))
				.forEach(reader -> setReader(route, reader));

			readers.get(Boolean.TRUE).stream().distinct()
				.filter(transformer -> transformer.isReaderCompatible(route))
				.forEach(reader -> setReader(route, reader));
		}
	}

	private static <X extends Writer> void populateWriter(Map<Boolean, List<X>> writers, EntityRoute route, boolean condition) {
		if (condition) {
			writers.get(Boolean.FALSE).stream().distinct()
				.filter(writer -> writer.isWriterCompatible(route))
				.forEach(writer -> setWriter(route, writer));

			writers.get(Boolean.TRUE).stream().distinct()
				.filter(writer -> writer.isWriterCompatible(route))
				.forEach(writer -> setWriter(route, writer));
		}
	}

	private static void logRoute(EntityRoute route) {
		log.info(() -> String.format(
				"Route instantiated: METHOD[%s], PATH[%s], CONSUMES[%s], PRODUCES[%s], REQ-CLASS[%s], RESP-CLASS[%s]",
				route.getHttpMethod(), route.getPath(), route.getConsumes(), route.getProduces(),
				route.getRequestType(), route.getResponseType()));
	}

	private static <T extends Transformer> void setReader(EntityRoute route, T reader) {
		if (reader instanceof Reader) {
			route.addReader((Reader) reader);
			log.debug(() -> String.format(
					"Reader [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
					reader.getClass().getSimpleName(), reader.getMediaType(), route.getHttpMethod(), route.getPath()));
		}
	}

	private static <T extends Transformer> void setWriter(EntityRoute route, T writer) {
		if (writer instanceof Writer) {
			route.addWriter((Writer) writer);
			log.debug(() -> String.format(
					"Writer [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
					writer.getClass().getSimpleName(), writer.getMediaType(), route.getHttpMethod(), route.getPath()));
		}
	}

	private static void setAspects(EntityRoute route, Aspect... aspects) {
		route.aspect(aspects);
		Arrays.stream(aspects).forEach(aspect ->
				log.debug(() -> String.format(
						"Aspect [%s] added to the Route [METHOD[%s], PATH[%s]]",
						aspect.getClass().getSimpleName(), route.getHttpMethod(), route.getPath())));
	}

	protected abstract <B> List<B> getBeans(Class<B> clazz);

}
