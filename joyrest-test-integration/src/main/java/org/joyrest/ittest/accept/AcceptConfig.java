package org.joyrest.ittest.accept;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcceptConfig {

    @Bean
    ControllerConfiguration acceptController() {
        return new AcceptController();
    }

}
