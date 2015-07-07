package org.joyrest.ittest.requestResponseData;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestResponseDataConfig {

    @Bean
    ControllerConfiguration requestResponseController() {
        return new RequestResponseDataController();
    }
}
