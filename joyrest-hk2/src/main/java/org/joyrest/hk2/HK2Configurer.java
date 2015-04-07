package org.joyrest.hk2;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.joyrest.context.AbstractConfigurer;
import org.joyrest.context.ApplicationContext;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Class that is able to configure JoyREST Framework for HK2 Dependency Injection Framework.
 *
 * @author pbouda
 */
public final class HK2Configurer extends AbstractConfigurer<Binder> {

	private ServiceLocator locator = null;

	@Override
	protected <B> List<B> getBeans(Class<B> clazz) {
		return locator.getAllServices(clazz);
	}

	@Override
	public ApplicationContext initialize(Binder applicationConfig) {
		requireNonNull(applicationConfig, "ApplicationConfig must be non-null for configuring HK2.");

		ServiceLocatorFactory locatorFactory = ServiceLocatorFactory.getInstance();
		locator = locatorFactory.create(JOY_REST_BEAN_CONTEXT);
		ServiceLocatorUtilities.bind(locator, applicationConfig);

		return initializeContext();
	}

}
