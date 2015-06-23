package org.joyrest.context.autoconfigurar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		getClass("org.joyrest.gson.GsonReaderWriter").ifPresent(gson -> processReaderWriter(gson, list));
		getClass("org.joyrest.hessian.HessianReaderWriter").ifPresent(hessian -> processReaderWriter(hessian, list));
		getClass("org.joyrest.jackson.JacksonReaderWriter").ifPresent(jackson -> processReaderWriter(jackson, list));
		return list;
	}

	private static void processReaderWriter(Class<?> clazz, List<AbstractReaderWriter> list) {
		try {
			list.add((AbstractReaderWriter) clazz.newInstance());
			log.debug(() -> String.format("Class '%s' was added among Readers and Writers using auto-configuration.", clazz));
		} catch (Exception e) {
			throw new InvalidConfigurationException(format("Class %s is not valid ReaderWriter.", clazz));
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
