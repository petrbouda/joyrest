package org.joyrest.ittest.path;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathRouteConfig {

	@Bean
	ControllerConfiguration pathRouteController1() {
		return new PathRouteController1();
	}

	@Bean
	ControllerConfiguration pathRouteController2() {
		return new PathRouteController2();
	}

	@Bean
	ControllerConfiguration pathRouteController3() {
		return new PathRouteController3();
	}

}
