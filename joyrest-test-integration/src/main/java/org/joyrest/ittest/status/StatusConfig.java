package org.joyrest.ittest.status;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatusConfig {

    @Bean
    ControllerConfiguration statusController() {
        return new StatusController();
    }

}
