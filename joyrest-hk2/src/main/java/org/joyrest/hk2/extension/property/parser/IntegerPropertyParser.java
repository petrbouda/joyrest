package org.joyrest.hk2.extension.property.parser;

public class IntegerPropertyParser implements PropertyParser<Integer> {

    @Override
    public Integer apply(String s) {
        return Integer.parseInt(s);
    }

}
