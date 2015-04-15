package org.joyrest.extractor.param;

import java.util.Optional;

public interface PathType<T> {

	Optional<T> valueOf(String value);

	String getName();

	boolean isAssignableFromString(String obj);

}
