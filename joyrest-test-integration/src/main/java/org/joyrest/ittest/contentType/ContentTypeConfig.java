package org.joyrest.ittest.contentType;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentTypeConfig {

    @Bean
    ControllerConfiguration contentTypeController() {
        return new ContentTypeController();
    }

}
