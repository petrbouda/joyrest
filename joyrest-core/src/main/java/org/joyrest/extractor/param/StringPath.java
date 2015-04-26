package org.joyrest.extractor.param;

import java.util.function.Function;

public class StringPath extends AbstractPath<String> {

	public static final StringPath INSTANCE = new StringPath();
	public static final String NAME = "str";

	public StringPath() {
		super(NAME, Function.identity());
	}

}
