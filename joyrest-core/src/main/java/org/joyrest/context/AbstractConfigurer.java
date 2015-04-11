package org.joyrest.context;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.partitioningBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joyrest.aspect.Aspect;
import org.joyrest.collection.annotation.Default;
import org.joyrest.exception.ExceptionConfiguration;
import org.joyrest.logging.JoyLogger;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.routing.EntityRoute;
import org.joyrest.routing.Route;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Transformer;
import org.joyrest.transform.Writer;
import org.joyrest.transform.aspect.SerializationAspect;

@SuppressWarnings("rawtypes")
public abstract class AbstractConfigurer<T> implements Configurer<T> {

	private final static JoyLogger log = new JoyLogger(AbstractConfigurer.class);

	public static final List<Aspect> REQUIRED_ASPECTS = Arrays.asList(new SerializationAspect());

	/* ServiceLocator name in its own context */
	public static final String JOY_REST_BEAN_CONTEXT = "JoyRestBeanContext";

	protected abstract <B> List<B> getBeans(Class<B> clazz);

	protected ApplicationContext initializeContext() {
		Map<Boolean, List<Reader>> readers = getBeans(Reader.class)
			.stream().collect(partitioningBy(Default::isDefault));

		Map<Boolean, List<Writer>> writers = getBeans(Writer.class)
			.stream().collect(partitioningBy(Default::isDefault));

		List<Aspect> aspects = getBeans(Aspect.class);
		aspects.addAll(REQUIRED_ASPECTS);

		List<ExceptionConfiguration> handlers = getBeans(ExceptionConfiguration.class);
		handlers.forEach(ExceptionConfiguration::initialize);

		List<ControllerConfiguration> routers = getBeans(ControllerConfiguration.class);

		Stream<EntityRoute<?, ?>> routeStream = routers.stream()
			.peek(ControllerConfiguration::initialize)
			.flatMap(config -> config.getRoutes().stream())
			.peek(AbstractConfigurer::logRoute)
			.peek(route -> setAspects(route, aspects.toArray(new Aspect[aspects.size()])))
			.peek(route -> populateTransforms(readers, route, AbstractConfigurer::setReader, nonNull(route.getRequestType())))
			.peek(route -> populateTransforms(writers, route, AbstractConfigurer::setWriter, nonNull(route.getResponseType())));

		Set<EntityRoute<?, ?>> routes = routeStream.collect(Collectors.toSet());

		ApplicationContext joyContext = new ApplicationContextImpl();
		joyContext.addRoutes(routes);
		joyContext.addExceptionHandlers(handlers);
		return joyContext;
	}

	private static <X extends Transformer> void populateTransforms(Map<Boolean, List<X>> transformers,
			EntityRoute<?, ?> route, BiConsumer<EntityRoute, Transformer> setter, boolean condition) {
		if(condition) {
			transformers.get(Boolean.FALSE).stream()
				.distinct()
				.filter(transformer -> transformer.isCompatible(route))
				.forEach(transformer -> setter.accept(route, transformer));

			transformers.get(Boolean.TRUE).stream()
				.distinct()
				.filter(transformer -> transformer.isCompatible(route))
				.forEach(transformer -> setter.accept(route, transformer));
		}
	}

	private static void logRoute(Route<?, ?> route) {
		log.info(() -> String.format(
				"Route instantiated: METHOD[%s], PATH[%s], CONSUMES[%s], PRODUCES[%s], REQ-CLASS[%s], RESP-CLASS[%s]",
				route.getHttpMethod(), route.getPath(), route.getConsumes(), route.getProduces(),
				route.getRequestType(), route.getResponseType()));
	}

	private static <T extends Transformer> void setReader(EntityRoute<?, ?> route, T reader) {
		if (reader instanceof Reader) {
			route.addReader((Reader) reader);
			log.debug(() -> String.format(
					"Reader [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
					reader.getClass().getSimpleName(), reader.getMediaType(), route.getHttpMethod(), route.getPath()));
		}
	}

	private static <T extends Transformer> void setWriter(EntityRoute<?, ?> route, T writer) {
		if (writer instanceof Writer) {
			route.addWriter((Writer) writer);
			log.debug(() -> String.format(
					"Writer [%s, %s] added to the Route [METHOD[%s], PATH[%s]]",
					writer.getClass().getSimpleName(), writer.getMediaType(), route.getHttpMethod(), route.getPath()));
		}
	}

	private static void setAspects(EntityRoute<?, ?> route, Aspect... aspects) {
		route.aspect(aspects);
		Arrays.stream(aspects).forEach(aspect ->
				log.debug(() -> String.format(
						"Aspect [%s] added to the Route [METHOD[%s], PATH[%s]]",
						aspect.getClass().getSimpleName(), route.getHttpMethod(), route.getPath())));
	}

}
