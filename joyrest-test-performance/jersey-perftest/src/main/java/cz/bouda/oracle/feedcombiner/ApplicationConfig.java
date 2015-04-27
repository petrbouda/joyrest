package cz.bouda.oracle.feedcombiner;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import cz.bouda.oracle.feedcombiner.binder.ApplicationBinder;

@ApplicationPath("/services")
public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		packages("cz.bouda.oracle.feedcombiner");
		register(MoxyJsonFeature.class);
		register(new ApplicationBinder());
	}
}
