package org.joyrest.hk2.extension.property.parser;

import java.util.function.Function;

import org.jvnet.hk2.annotations.Contract;

@Contract
@FunctionalInterface
public interface PropertyParser<T> extends Function<String, T> {

}
