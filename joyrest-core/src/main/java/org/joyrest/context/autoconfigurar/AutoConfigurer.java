package org.joyrest.context.autoconfigurar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joyrest.context.initializer.Initializer;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.logging.JoyLogger;
import org.joyrest.transform.AbstractReaderWriter;
import org.joyrest.transform.StringReaderWriter;

import static java.lang.String.format;

public class AutoConfigurer {

	private static final JoyLogger log = new JoyLogger(AutoConfigurer.class);

	public static List<AbstractReaderWriter> configureReadersWriters() {
		List<AbstractReaderWriter> list = new ArrayList<>();
		list.add(new StringReaderWriter());

		getClass("org.joyrest.gson.GsonReaderWriter").ifPresent(gson -> processBean(gson, list));
		getClass("org.joyrest.hessian.HessianReaderWriter").ifPresent(hessian -> processBean(hessian, list));
		getClass("org.joyrest.jackson.JacksonReaderWriter").ifPresent(jackson -> processBean(jackson, list));
		return list;
	}

	public static List<Initializer> configureInitializers() {
		List<Initializer> list = new ArrayList<>();
		getClass("org.joyrest.oauth2.initializer.OAuth2Initializer").ifPresent(initializer -> processBean(initializer, list));
		return list;
	}

	@SuppressWarnings("unchecked")
	private static <B> void processBean(Class<?> clazz, List<B> list) {
		try {
			list.add((B) clazz.newInstance());
			log.debug(() -> String.format("Class '%s' was added using auto-configuration.", clazz));
		} catch (Exception e) {
			throw new InvalidConfigurationException(format("Class %s is not valid.", clazz));
		}
	}

	protected static Optional<Class<?>> getClass(String className) {
		try {
			ClassLoader classLoader = AutoConfigurer.class.getClassLoader();
			return Optional.of(classLoader.loadClass(className));
		} catch (Throwable ex) {
			return Optional.empty();
		}
	}
}
