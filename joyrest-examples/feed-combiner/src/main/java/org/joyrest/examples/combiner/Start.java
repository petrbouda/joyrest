package org.joyrest.examples.combiner;

import org.joyrest.context.ApplicationContext;
import org.joyrest.examples.combiner.binder.ApplicationBinder;
import org.joyrest.grizzly.GrizzlyServer;
import org.joyrest.hk2.HK2Configurer;

public class Start {

    public static final String PROPS_FILE = "application.properties";

    public static void main(String... args) throws Exception {
        HK2Configurer configurer = new HK2Configurer();
        ApplicationContext applicationContext = configurer.initialize(new ApplicationBinder(PROPS_FILE));
        GrizzlyServer.start(applicationContext, 5000, "/services");
    }

}
