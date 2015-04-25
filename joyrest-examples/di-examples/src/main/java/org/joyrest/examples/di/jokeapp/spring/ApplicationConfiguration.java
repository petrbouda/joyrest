package org.joyrest.examples.di.jokeapp.spring;

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.JsonReaderWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    JsonReaderWriter jsonReaderWriter(){
        return new JsonReaderWriter();
    }

    @Bean
    ControllerConfiguration jokeControllerConfiguration() {
        return new JokeController();
    }

    @Bean
    JokeService jokeService() {
        return new JokeServiceImpl();
    }

}
