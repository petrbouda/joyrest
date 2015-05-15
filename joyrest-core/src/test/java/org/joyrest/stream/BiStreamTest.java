package org.joyrest.stream;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joyrest.exception.type.RestException;
import org.junit.Test;

public class BiStreamTest {

	private final List<String> names = Arrays.asList("George", "Maria", "Peter");

	@Test
	public void success() {
		BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
		Optional<String> result = stream
			.throwIfNull((name, activity) -> name.equals("Maria"),
				internalServerErrorSupplier("Maria was not found."))
			.throwIfNull((name, activity) -> name.contains("ria"),
				internalServerErrorSupplier("'ria' string not found."))
			.throwIfNull((name, activity) -> name.contains("Ma"),
				internalServerErrorSupplier("'Ma' string not found."))
			.findAny();

		assertEquals("Maria", result.get());
	}

	@Test
	public void success_multiple() {
		BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
		Optional<String> result = stream
			.throwIfNull((name, activity) -> true,
				internalServerErrorSupplier("Nobody was not found."))
			.findAny();

		assertEquals("George", result.get());
	}

	@Test
	public void exception_empty_stream() {
		BiStream<String, String> stream = BiStream.of(names.stream(), "Hunter");
		try {
			stream
				.throwIfNull((name, activity) -> name.equals("Maria"),
					internalServerErrorSupplier("Maria was not found."))
				.throwIfNull((name, activity) -> name.contains("ria"),
					internalServerErrorSupplier("'ria' string not found."))
				.throwIfNull((name, activity) -> name.contains("Some String"),
					internalServerErrorSupplier("'Some String' string not found."))
				.findAny();
		} catch (RestException ex) {
			assertEquals("'Some String' string not found.", ex.getMessage());
		}
	}

}