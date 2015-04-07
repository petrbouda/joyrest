package org.joyrest.hk2.extension.property;

import org.glassfish.hk2.api.HK2RuntimeException;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class PropertyResolver implements InjectionResolver<Property> {

	private final Map<String, String> properties;
	private final Map<Type, Function<String, ?>> parsers;

	public PropertyResolver(Map<String, String> properties, Map<Type, Function<String, ?>> parsers) {
		this.properties = properties;
		this.parsers = parsers;
	}

	@Override
	public Object resolve(Injectee injectee, ServiceHandle<?> serviceHandle) {
		Property annotation = injectee.getParent().getAnnotation(Property.class);

		String property = properties.get(annotation.value());
		if (property == null || property.isEmpty()) {
			throw new HK2RuntimeException(String.format("Property %s does not exist.", property));
		}

		Function<String, ?> parser = parsers.get(injectee.getRequiredType());
		return parser.apply(property);
	}

	@Override
	public boolean isConstructorParameterIndicator() {
		return true;
	}

	@Override
	public boolean isMethodParameterIndicator() {
		return true;
	}

}
