package org.joyrest.examples.di.jokeapp.spring;

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.gson.GsonReaderWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    GsonReaderWriter jsonReaderWriter(){
        return new GsonReaderWriter();
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
