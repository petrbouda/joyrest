package org.joyrest.utils;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joyrest.extractor.param.StringVariable;
import org.joyrest.model.RoutePart;
import org.junit.Test;

public class PathUtilsTest {

	@Test
	public void path_multiple_parts() throws Exception {
		List<String> parts = PathUtils.createPathParts("/service/jokes/single");
		assertEquals(asList("service", "jokes", "single"), parts);
	}

	@Test
	public void path_one_part() throws Exception {
		List<String> parts = PathUtils.createPathParts("/service");
		assertEquals(singletonList("service"), parts);
	}

	@Test
	public void path_null_path() throws Exception {
		List<String> parts = PathUtils.createPathParts(null);
		assertEquals(emptyList(), parts);
	}

	@Test
	public void path_double_slash() throws Exception {
		List<String> parts = PathUtils.createPathParts("/service//jokes");
		assertEquals(asList("service", "jokes"), parts);
	}

	@Test
	public void path_whitespace() throws Exception {
		List<String> parts = PathUtils.createPathParts("/service/ jokes ");
		assertEquals(asList("service", "jokes"), parts);
	}

	@Test
	public void route_null_path() throws Exception {
		List<RoutePart<String>> parts = PathUtils.createRoutePathParts(null);
		assertEquals(emptyList(), parts);
	}

	@Test
	public void route_multiple_parts() throws Exception {
		List<RoutePart<String>> routePathParts = PathUtils.createRoutePathParts("/service/jokes/single");
		assertEquals(asList(
				new RoutePart<>(RoutePart.Type.PATH, "service", StringVariable.INSTANCE),
				new RoutePart<>(RoutePart.Type.PATH, "jokes", StringVariable.INSTANCE),
				new RoutePart<>(RoutePart.Type.PATH, "single", StringVariable.INSTANCE)), routePathParts);
	}
}