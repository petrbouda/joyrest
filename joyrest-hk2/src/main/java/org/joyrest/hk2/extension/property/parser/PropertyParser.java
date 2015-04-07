package org.joyrest.hk2.extension.property.parser;

import org.jvnet.hk2.annotations.Contract;

import java.util.function.Function;

@Contract
@FunctionalInterface
public interface PropertyParser<T> extends Function<String, T> {

}
