package org.joyrest.routing;

import java.util.Set;

/**
 *  A basic configuration interface contains method {@link #initialize()} in which is mostly
 *  set of {@link AbstractRoute} classes represent an individual handler
 *  for client's request.
 *
 * @author pbouda
 **/
public interface ControllerConfiguration {

    /**
     * Method contains a configuration of {@link Route} classes and
     * other objects which are in a connection with these routes.
     *
     * This method is considered as a entry point for configuration of routes.
     **/
    void initialize();

    /**
     * Provides set routes configured in {@link #initialize()} method of the class
     * implementing this interface.
     *
     * @return set of routes which are configured in {@link #initialize()} method
     **/
    Set<AbstractRoute> getRoutes();

}
