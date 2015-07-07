package org.joyrest.ittest;

import org.joyrest.servlet.ServletApplicationHandler;
import org.joyrest.spring.SpringJavaConfigurer;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({EmbeddedServletContainerAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class})
public class ApplicationConfig {

    // @Bean
    // JacksonReaderWriter jsonReaderRegistrar() {
    // return new JacksonReaderWriter();
    // }

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