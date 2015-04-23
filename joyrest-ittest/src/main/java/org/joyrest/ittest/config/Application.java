package org.joyrest.ittest.config;

import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;

@Configuration
@Import({ EmbeddedServletContainerAutoConfiguration.class,  PropertyPlaceholderAutoConfiguration.class })
@ComponentScan
public class Application {

}