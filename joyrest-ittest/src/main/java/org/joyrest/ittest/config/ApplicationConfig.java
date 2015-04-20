package org.joyrest.ittest.config;

import org.joyrest.ittest.ContentTypeController;
import org.joyrest.ittest.RequestResponseDataController;
import org.joyrest.ittest.StatusController;
import org.joyrest.ittest.TestExceptionConfiguration;
import org.joyrest.ittest.exception.ExceptionController;
import org.joyrest.ittest.path.PathRouteController1;
import org.joyrest.ittest.path.PathRouteController2;
import org.joyrest.ittest.path.PathRouteController3;
import org.joyrest.ittest.route.DeleteRouteController;
import org.joyrest.ittest.route.GetRouteController;
import org.joyrest.ittest.route.PostRouteController;
import org.joyrest.ittest.route.PutRouteController;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.servlet.ServletApplicationHandler;
import org.joyrest.spring.SpringJavaConfigurer;
import org.joyrest.transform.JsonReaderWriter;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	// STATUS -----------------------------------------

	@Bean
	ControllerConfiguration statusController() {
		return new StatusController();
	}

	// ROUTE ------------------------------------------

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

	// PATH -------------------------------------------

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

	// CONTENT-TYPE -----------------------------------

	@Bean
	ControllerConfiguration contentTypeController() {
		return new ContentTypeController();
	}

	// REQUEST-RESPONSE-TEST --------------------------

	@Bean
	ControllerConfiguration requestResponseController() {
		return new RequestResponseDataController();
	}

	// EXCEPTION-TEST ---------------------------------

	@Bean
	ControllerConfiguration exceptionController() {
		return new ExceptionController();
	}

	@Bean
	TestExceptionConfiguration testExceptionConfiguration() {
		return new TestExceptionConfiguration();
	}

	@Bean
	JsonReaderWriter jsonReaderRegistrar() {
		return new JsonReaderWriter();
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(
				new ServletApplicationHandler(new SpringJavaConfigurer(), new ApplicationConfig()), "/*");
	}

}
