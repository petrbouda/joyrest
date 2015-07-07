package org.joyrest.ittest.params;

import org.joyrest.routing.ControllerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamsConfig {

    @Bean
    ControllerConfiguration paramsController() {
        return new ParamsController();
    }

}
