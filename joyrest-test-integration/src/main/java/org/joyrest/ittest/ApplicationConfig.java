package org.joyrest.ittest;

import org.joyrest.servlet.ServletApplicationHandler;
import org.joyrest.spring.SpringJavaConfigurer;
import org.joyrest.utils.transform.JsonReaderWriter;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
@Import({ EmbeddedServletContainerAutoConfiguration.class,  PropertyPlaceholderAutoConfiguration.class })
public class ApplicationConfig {

	@Bean
	JsonReaderWriter jsonReaderRegistrar() {
		return new JsonReaderWriter();
	}

	@Bean
	CommonExceptionConfiguration commonExceptionConfiguration() {
		return new CommonExceptionConfiguration();
	}


	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(
			new ServletApplicationHandler(new SpringJavaConfigurer(), new ApplicationConfig()), "/*");
	}

}