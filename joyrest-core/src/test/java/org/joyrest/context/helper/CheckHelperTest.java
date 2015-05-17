package org.joyrest.context.helper;

import org.joyrest.aspect.Aspect;
import org.joyrest.context.helper.aspects.DuplicatedAspect;
import org.joyrest.context.helper.aspects.FirstAspect;
import org.joyrest.context.helper.aspects.SecondAspect;
import org.joyrest.context.helper.aspects.ThirdAspect;
import org.joyrest.exception.type.InvalidConfigurationException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.joyrest.context.helper.CheckHelper.orderDuplicationCheck;
import static org.junit.Assert.*;

public class CheckHelperTest {

	@Test
	public void with_correct_orders() {
		List<Aspect> aspects = Arrays.asList(new FirstAspect(), new SecondAspect(), new ThirdAspect());
		try {
			orderDuplicationCheck(aspects);
		} catch (InvalidConfigurationException e) {
			fail("There is some problem with order duplications");
		}
	}

	@Test(expected = InvalidConfigurationException.class)
	public void with_duplication_exception() {
		List<Aspect> aspects = Arrays.asList(new FirstAspect(), new SecondAspect(), new DuplicatedAspect());
		orderDuplicationCheck(aspects);
	}
}