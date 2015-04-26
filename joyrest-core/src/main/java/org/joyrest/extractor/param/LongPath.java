package org.joyrest.extractor.param;

public class LongPath extends AbstractPath<Long> {

	public static final LongPath INSTANCE = new LongPath();
	public static final String NAME = "long";

	private LongPath() {
		super(NAME, Long::parseLong);
	}

}
