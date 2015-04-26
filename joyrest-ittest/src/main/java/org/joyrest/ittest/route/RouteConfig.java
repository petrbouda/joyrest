package org.joyrest.ittest.route;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Bean
	ControllerConfiguration postRouteController() {
		return new PostRouteController();
	}

	@Bean
	ControllerConfiguration getRouteController() {
		return new GetRouteController();
	}

	@Bean
	ControllerConfiguration putRouteController() {
		return new PutRouteController();
	}

	@Bean
	ControllerConfiguration deleteRouteController() {
		return new DeleteRouteController();
	}

}
