package org.joyrest.extractor.param;

import java.util.function.Function;

public class StringPath extends AbstractPath<String> {

    public static final String NAME = "string";

    public static final StringPath INSTANCE = new StringPath();

    private static final Function<String, String> EXTRACTOR = Function.identity();

    private StringPath() {
        super(NAME, EXTRACTOR);
    }

}
