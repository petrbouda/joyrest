package org.joyrest.ittest.routes;

import org.joyrest.ittest.routes.route.DeleteRouteController;
import org.joyrest.ittest.routes.route.GetRouteController;
import org.joyrest.ittest.routes.route.PostRouteController;
import org.joyrest.ittest.routes.route.PutRouteController;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.servlet.ServletApplicationHandler;
import org.joyrest.spring.SpringJavaConfigurer;
import org.joyrest.transform.*;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfig {

	@Bean
	ControllerConfiguration statusController() {
		return new StatusController();
	}

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

	@Bean
	Reader jsonReaderRegistrar() {
		return new JsonReader();
	}

	@Bean
	Writer jsonWriterRegistrar() {
		return new JsonWriter();
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(
				new ServletApplicationHandler(new SpringJavaConfigurer(), new ApplicationConfig()), "/*");
	}

}
