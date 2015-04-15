package org.joyrest.context;

import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.partitioningBy;

import java.util.*;
import java.util.stream.Collectors;

import org.joyrest.aspect.Aspect;
import org.joyrest.common.annotation.General;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.exception.handler.ExceptionHandler;
import org.joyrest.logging.JoyLogger;
import org.joyrest.model.http.MediaType;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.EntityRoute;
import org.joyrest.transform.Reader;
import org.joyrest.transform.StringReaderWriter;
import org.joyrest.transform.Transformer;
import org.joyrest.transform.Writer;
import org.joyrest.transform.aspect.SerializationAspect;

@SuppressWarnings("rawtypes")
public abstract class AbstractConfigurer<T> implements Configurer<T> {

	/* ServiceLocator name in its own context */
	public static final String JOYREST_BEAN_CONTEXT = "JoyRestBeanContext";
	private final static JoyLogger log = new JoyLogger(AbstractConfigurer.class);
	public final List<Aspect> REQUIRED_ASPECTS = Collections.singletonList(new SerializationAspect());
	private final StringReaderWriter stringReaderWriter = new StringReaderWriter();
	public final List<Reader> REQUIRED_READERS = Collections.singletonList(stringReaderWriter);
	public final List<Writer> REQUIRED_WRITERS = Collections.singletonList(stringReaderWriter);

	private static <T extends Transformer> Map<Boolean, List<T>> createTransformers(List<T> writers, List<T> additional) {
		writers.addAll(additional);
		return writers.stream()
			.collect(partitioningBy(General::isGeneral));
	}

	private static Map<MediaType, Writer> createExceptionWriters(Map<Boolean, List<Writer>> writers) {
		return writers.values().stream()
			.flatMap(List::stream)
			.distinct()
			.collect(Collectors.toMap(Transformer::getMediaType, identity()));
	}

	private static <X extends Reader> void populateReader(Map<Boolean, List<X>> readers,
			EntityRoute route, boolean condition) {
		if (condition) {
			readers.get(Boolean.FALSE).stream().distinct()
				.filter(reader -> reader.isReaderCompatible(route))
				.forEach(reader -> setReader(route, reader));

			readers.get(Boolean.TRUE).stream().distinct()
				.filter(transformer -> transformer.isReaderCompatible(route))
				.forEach(reader -> setReader(route, reader));
		}
	}

	private static <X extends Writer> void populateWriter(Map<Boolean, List<X>> writers,
			EntityRoute route, boolean condition) {
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

	protected ApplicationContext initializeContext() {
		Map<Boolean, List<Reader>> readers = createTransformers(getBeans(Reader.class), REQUIRED_READERS);
		Map<Boolean, List<Writer>> writers = createTransformers(getBeans(Writer.class), REQUIRED_WRITERS);

		Map<Class<? extends Exception>, ExceptionHandler<? super Exception>> handlers =
				getBeans(ExceptionConfiguration.class).stream().peek(ExceptionConfiguration::initialize)
					.flatMap(config -> config.getExceptionHandlers().entrySet().stream())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		List<Aspect> aspects = getBeans(Aspect.class);
		aspects.addAll(REQUIRED_ASPECTS);

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

}
