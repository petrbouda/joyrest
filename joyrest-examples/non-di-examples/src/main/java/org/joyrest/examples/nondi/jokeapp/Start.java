package org.joyrest.examples.nondi.jokeapp;

import org.joyrest.context.ApplicationContext;
import org.joyrest.context.configurer.ApplicationConfiguration;
import org.joyrest.context.configurer.NonDiConfigurer;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.jackson.JacksonReaderWriter;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class Start {

    public static void main(String... args) throws Exception {
        JacksonReaderWriter readerWriter = new JacksonReaderWriter();
        JokeController jokeController = new JokeController(new JokeServiceImpl());

        ApplicationConfiguration beanContext = new ApplicationConfiguration();
        beanContext.add(ControllerConfiguration.class, jokeController);
        beanContext.add(Reader.class, readerWriter);
        beanContext.add(Writer.class, readerWriter);

        ApplicationContext applicationContext = new NonDiConfigurer().initialize(beanContext);
        GrizzlyServer.start(applicationContext, 5000, "/services");
    }
}
