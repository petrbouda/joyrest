package org.joyrest.aspect;

import java.util.function.Predicate;

import org.joyrest.routing.Route;

public interface GlobalAspect extends Aspect, Predicate<Route> {

}
