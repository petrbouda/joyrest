package org.joyrest.extractor.param;

import java.util.function.Function;

public class IntegerPath extends AbstractPath<Integer> {

    public static final String NAME = "int";
    public static final IntegerPath INSTANCE = new IntegerPath();

    private static final Function<String, Integer> EXTRACTOR = Integer::getInteger;

    private IntegerPath() {
        super(NAME, EXTRACTOR);
    }

}
