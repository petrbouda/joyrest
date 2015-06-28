package org.joyrest.ittest.aspect;

import org.joyrest.aspect.Interceptor;
import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;

//@Configuration
public class AspectConfig {

	@Bean
	ControllerConfiguration aspectController() {
		return new AspectController();
	}

	@Bean
	Interceptor firstAspect() {
		return new FirstInterceptor();
	}

	@Bean
	Interceptor secondAspect() {
		return new SecondInterceptor();
	}

	@Bean
	Interceptor thirdAspect() {
		return new ThirdInterceptor();
	}

}
