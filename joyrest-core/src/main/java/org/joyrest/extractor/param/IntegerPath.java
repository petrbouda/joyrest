package org.joyrest.extractor.param;

public class IntegerPath extends AbstractPath<Integer> {

	public static final IntegerPath INSTANCE = new IntegerPath();
	public static final String NAME = "int";

	private IntegerPath() {
		super(NAME, Integer::parseInt);
	}

}
