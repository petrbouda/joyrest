package org.joyrest.examples.combiner.binder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.joyrest.examples.combiner.ApplicationProperties;
import org.joyrest.examples.combiner.generator.IdGenerator;
import org.joyrest.examples.combiner.generator.SequenceIdGenerator;
import org.joyrest.examples.combiner.manager.FeedDataStoreManager;
import org.joyrest.examples.combiner.manager.FeedTaskFactory;
import org.joyrest.examples.combiner.manager.FeedTaskFactoryImpl;
import org.joyrest.examples.combiner.model.CombinedFeed;
import org.joyrest.examples.combiner.routes.CombinedFeedRoute;
import org.joyrest.examples.combiner.service.CombinedFeedService;
import org.joyrest.examples.combiner.service.CrudService;
import org.joyrest.examples.combiner.store.DataStoreObserver;
import org.joyrest.examples.combiner.store.InMemoryDataStore;
import org.joyrest.examples.combiner.store.ObservableDataStore;
import org.joyrest.examples.combiner.store.ReadWriteLockDataStore;
import org.joyrest.hk2.extension.property.Property;
import org.joyrest.hk2.extension.property.PropertyResolver;
import org.joyrest.hk2.extension.property.parser.IntegerPropertyParser;
import org.joyrest.hk2.extension.property.parser.LongPropertyParser;
import org.joyrest.routing.ControllerConfiguration;

public class ApplicationBinder extends AbstractBinder {

	private static Logger LOG = Logger.getLogger(PropertiesBinder.class.getName());

	private final Properties properties;

	public ApplicationBinder() {
		this("application.properties");
	}

	public ApplicationBinder(String propertiesFileName) {
		this.properties = getProperties(propertiesFileName);
	}

	private static Properties getProperties(String path) {
		try (InputStream inputStream = ApplicationBinder.class.getClassLoader().getResourceAsStream(path)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (Exception e) {
			LOG.warning("Property file '" + path + "' not found in the classpath");
		}
		return null;
	}

	@Override
	protected void configure() {
		ReadWriteLockDataStore datastore = new ReadWriteLockDataStore();

		install(
				new RouteBinder(),
				new PropertiesBinder(properties),
				new ResourcePartBinder(datastore),
				new SchedulerPartBinder(datastore, properties));
	}

	private static class RouteBinder extends AbstractBinder {

		@Override
		protected void configure() {
			bind(CombinedFeedRoute.class)
				.to(ControllerConfiguration.class)
				.in(Singleton.class);
		}

	}

	private static class SchedulerPartBinder extends AbstractBinder {

		private final ObservableDataStore dataStore;
		private final Properties properties;

		SchedulerPartBinder(ObservableDataStore datastore, Properties properties) {
			this.dataStore = datastore;
			this.properties = properties;
		}

		@Override
		protected void configure() {
			FeedTaskFactory feedTaskFactory = new FeedTaskFactoryImpl(dataStore);

			FeedDataStoreManager dataStoreManager;
			if (properties.containsKey(ApplicationProperties.SCHEDULER_POOL_SIZE)) {
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
					Integer.parseInt((String) properties.get(ApplicationProperties.SCHEDULER_POOL_SIZE)));
				dataStoreManager = new FeedDataStoreManager(feedTaskFactory, scheduler);
			} else {
				dataStoreManager = new FeedDataStoreManager(feedTaskFactory);
			}

			dataStore.addObserver(dataStoreManager);

			bind(feedTaskFactory)
				.to(FeedTaskFactory.class);

			bind(dataStoreManager)
				.to(FeedDataStoreManager.class)
				.to(DataStoreObserver.class);
		}

	}

	private static class ResourcePartBinder extends AbstractBinder {

		private final InMemoryDataStore datastore;

		ResourcePartBinder(InMemoryDataStore datastore) {
			this.datastore = datastore;
		}

		@Override
		protected void configure() {
			bind(datastore)
				.to(InMemoryDataStore.class);

			bind(CombinedFeedService.class)
				.to(new TypeLiteral<CrudService<CombinedFeed>>() {})
				.in(Singleton.class);

			bind(SequenceIdGenerator.class)
				.to(IdGenerator.class)
				.in(Singleton.class);
		}

	}

	private static class PropertiesBinder extends AbstractBinder {

		private final Properties properties;

		PropertiesBinder(Properties properties) {
			this.properties = properties;
		}

		@Override
		protected void configure() {
			if (nonNull(properties)) {
				Map<String, String> propertyMap = properties.stringPropertyNames().stream()
					.collect(toMap(Function.identity(), properties::getProperty));

				Map<Type, Function<String, ?>> parsers = new HashMap<>();
				parsers.put(Integer.class, new IntegerPropertyParser());
				parsers.put(Long.class, new LongPropertyParser());
				parsers.put(String.class, Function.identity());

				bind(new PropertyResolver(propertyMap, parsers))
					.to(new TypeLiteral<InjectionResolver<Property>>() {});
			}
		}
	}
}
