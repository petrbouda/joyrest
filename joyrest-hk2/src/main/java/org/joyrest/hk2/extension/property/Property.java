package org.joyrest.hk2.extension.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD})
public @interface Property {

    /**
     * Returns a name of the property value which will be
     * injected at the place of {@link org.glassfish.hk2.api.Injectee}
     */
    String value();

}
