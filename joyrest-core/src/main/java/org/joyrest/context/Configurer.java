package org.joyrest.context;

/**
 * Implementation of this interface is able to configure JoyREST framework for specified dependency injection framework.
 *
 * @param <T> type of the configuration class that is specialized according to dependency injection framework
 * 
 * @author pbouda
 */
public interface Configurer<T> {

	/**
	 * Initializes a given dependency injection framework
	 *
	 * @param applicationConfig class with definition of the custom dependencies
	 * @return instance with all needed information for a successful running of the application
	 */
	ApplicationContext initialize(T applicationConfig);

}
