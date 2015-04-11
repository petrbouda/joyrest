package org.joyrest.ittest.routes;

import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.servlet.ServletApplicationHandler;
import org.joyrest.spring.SpringJavaConfigurer;
import org.joyrest.transform.JsonReader;
import org.joyrest.transform.JsonWriter;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	// @Bean
	// ControllerConfiguration statusController() {
	// return new StatusController();
	// }

	@Bean
	ControllerConfiguration routeController() {
		return new RouteController();
	}

	// @Bean
	// ControllerConfiguration contentController() {
	// return new ContentTypeController();
	// }
	//
	// @Bean
	// ControllerConfiguration aspectController() {
	// return new AspectController();
	// }

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
				new ServletApplicationHandler(new SpringJavaConfigurer(), new ApplicationConfig()), "/services/*");
	}

}
