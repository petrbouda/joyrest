package org.joyrest.ittest.exception;

import org.joyrest.exception.configuration.ExceptionConfiguration;
import org.joyrest.ittest.exception.writer.ContactXmlWriter;
import org.joyrest.ittest.exception.writer.UnknownWriter;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Writer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfig {

    @Bean
    Writer unknownWriter() {
        return new UnknownWriter();
    }

    @Bean
    Writer contactWriter() {
        return new ContactXmlWriter();
    }

    @Bean
    ControllerConfiguration exceptionController() {
        return new ExceptionController();
    }

    @Bean
    ExceptionConfiguration testExceptionConfiguration() {
        return new TestExceptionConfiguration();
    }

}
