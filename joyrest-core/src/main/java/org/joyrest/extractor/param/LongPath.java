package org.joyrest.extractor.param;

import java.util.function.Function;

public class LongPath extends AbstractPath<Long> {

	public static final String NAME = "long";

	public static final LongPath INSTANCE = new LongPath();

	private static final Function<String, Long> EXTRACTOR = Long::getLong;

	private LongPath() {
		super(NAME, EXTRACTOR);
	}

}
