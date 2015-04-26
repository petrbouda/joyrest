package org.joyrest.ittest.aspect;

import org.joyrest.aspect.Aspect;
import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

	@Bean
	ControllerConfiguration aspectController() {
		return new AspectController();
	}

	@Bean
	Aspect firstAspect() {
		return new FirstAspect();
	}

	@Bean
	Aspect secondAspect() {
		return new SecondAspect();
	}

	@Bean
	Aspect thirdAspect() {
		return new ThirdAspect();
	}

}
