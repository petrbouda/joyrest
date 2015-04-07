package org.joyrest.hk2.extension.property.parser;

public class LongPropertyParser implements PropertyParser<Long> {

	@Override
	public Long apply(String s) {
		return Long.parseLong(s);
	}

}
