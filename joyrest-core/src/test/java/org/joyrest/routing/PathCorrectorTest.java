package org.joyrest.routing;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathCorrectorTest {

	private final PathCorrector testedClass = new PathCorrector();

	@Test
	public void null_path(){
		String result = testedClass.apply(null);
		assertEquals("/", result);
	}

	@Test(expected = InvalidConfigurationException.class)
	public void contains_double_slash() {
		testedClass.apply("/services//jokes");
	}

	@Test
	public void contains_single_slash() {
		String result = testedClass.apply("/");
		assertEquals("/", result);
	}

	@Test
	public void contains_blank_Path() {
		String result = testedClass.apply("");
		assertEquals("/", result);
	}

	@Test
	public void contains_starts_slash() {
		String result = testedClass.apply("/services/jokes/path");
		assertEquals("/services/jokes/path", result);
	}

	@Test
	public void contains_both_slash() {
		String result = testedClass.apply("/services/jokes/path/");
		assertEquals("/services/jokes/path", result);
	}

	@Test
	public void contains_both_without_slash() {
		String result = testedClass.apply("services/jokes/path");
		assertEquals("/services/jokes/path", result);
	}

	@Test
	public void contains_ends_slash() {
		String result = testedClass.apply("services/jokes/path/");
		assertEquals("/services/jokes/path", result);
	}
}