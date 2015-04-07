package org.joyrest.aspect;

import org.joyrest.routing.Route;

import java.util.function.Predicate;

public interface GlobalAspect extends Aspect, Predicate<Route> {

}
