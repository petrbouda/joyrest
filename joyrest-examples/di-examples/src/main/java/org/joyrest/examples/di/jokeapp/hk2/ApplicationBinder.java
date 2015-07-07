package org.joyrest.examples.di.jokeapp.hk2;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.joyrest.examples.di.jokeapp.JokeController;
import org.joyrest.examples.di.jokeapp.JokeService;
import org.joyrest.examples.di.jokeapp.JokeServiceImpl;
import org.joyrest.gson.GsonReaderWriter;
import org.joyrest.routing.ControllerConfiguration;
import org.joyrest.transform.Reader;
import org.joyrest.transform.Writer;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(JokeServiceImpl.class)
            .to(new TypeLiteral<JokeService>() {})
            .in(Singleton.class);

        bind(JokeController.class)
            .to(ControllerConfiguration.class)
            .in(Singleton.class);

        bind(new GsonReaderWriter())
            .to(Reader.class)
            .to(Writer.class);
    }
}
